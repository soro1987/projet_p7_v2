package fr.soro.service;

import fr.soro.dto.EmailTemplateDTO;
import fr.soro.entities.*;
import fr.soro.repositories.*;
import fr.soro.utilities.ReservationTimerTask;
import fr.soro.utilities.ReservationTimers;
import fr.soro.utilities.UtilitiesComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Service
public class EmpruntService {

	@Autowired
	private EmpruntRepository empruntRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	ReservationTimers timers;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ExemplaireRepository exemplaireRepository;

	@Autowired
	private OuvrageRepository ouvrageRepository;

	@Autowired
	private UtilitiesComponent utilitiesComponent;

	public EmpruntService(EmpruntRepository empruntRepository) {
		this.empruntRepository = empruntRepository;
	}

	public Emprunt get(Long id) {
		return this.empruntRepository.getOne(id);
	}

	public List<Emprunt> getDateDebut(Date datedebut) {
		return this.empruntRepository.findByDateDebut(datedebut);
	}

	public List<Emprunt> getDateEcheance(Date dateEcheance) {
		return this.empruntRepository.findByDateEcheance(dateEcheance);
	}

	public List<Emprunt> getProlongation(boolean prolongation) {
		return this.empruntRepository.findByProlongation(prolongation);
	}

	public List<Emprunt> getDepassement(int depassement) {
		return this.empruntRepository.findByDepassement(depassement);
	}

	public void deleted(Long id) {
		this.empruntRepository.deleteById(id);
	}

	public List<Emprunt> getAllEmprunt() {
		return this.empruntRepository.findAll();
	}
	
	public List<Emprunt> getAllExpireEmprunt() {
		List<Emprunt> emprunts = empruntRepository.findAll();
		List<Emprunt> empruntsExpirer = new ArrayList<Emprunt>();
		for(Emprunt emprunt : emprunts) 
		{
			Date dateDebut = emprunt.getDateDebut();
			Date dateEcheance = emprunt.getDateEcheance();
			if (dateDebut.after(dateEcheance))
			{
				empruntsExpirer.add(emprunt);					
			}			
		}		
		return empruntsExpirer;
	}
	
	public Emprunt save(Long idUser, Long idExmplaire, Emprunt emprunt) {
//		Emprunt emprunt = new Emprunt();
		emprunt.setDateDebut(new Date());
		Calendar calendrier = Calendar.getInstance();
		Date dateCourante = emprunt.getDateDebut();
		calendrier.setTime(dateCourante);
		calendrier.add(Calendar.HOUR, 24*28);
		emprunt.setDateEcheance(calendrier.getTime());
		User user = this.userRepository.getOne(idUser);
		emprunt.setUser(user);
		Exemplaire exemplaire = this.exemplaireRepository.getExemplaireById(idExmplaire);
		emprunt.getExemplaires().add(exemplaire);
		exemplaire.setDisponible(false);
		Emprunt empruntSaved  = this.empruntRepository.save(emprunt);
		user.getEmprunts().add(empruntSaved);
		exemplaire.setEmprunt(empruntSaved);
		
		this.userRepository.save(user);
		this.exemplaireRepository.save(exemplaire);
		return empruntSaved;
	}
	
	public Emprunt setProlongation(Long id) {
		Emprunt emprunt = this.get(id);
		if(emprunt.isProlongation() == false) {
			emprunt.setProlongation(true);
			Calendar calendrier = Calendar.getInstance();
			Date dateCourante = emprunt.getDateEcheance();
			calendrier.setTime(dateCourante);
			calendrier.add(Calendar.HOUR, 24*28);
			emprunt.setDateEcheance(calendrier.getTime());
			this.empruntRepository.save(emprunt);
		}
		return emprunt;
	}

	public void returnEmprunt(Long idEmprunt, Long idExmplaire) {
		Exemplaire exemplaire = this.exemplaireRepository.getExemplaireById(idExmplaire);
		exemplaire.setEmprunt(null);
		exemplaire.setDisponible(true);
		// check if there are any reservation made for the ouvrage returned
		//Trigger  runs when book is returned

		Ouvrage ouv = exemplaire.getOuvrage();
		ouv.setNbreExemplaireDispo(ouv.getNbreExemplaireDispo() + 1);
		ouvrageRepository.save(ouv);

		Optional<Reservation> topReserved =
				reservationRepository.findTopByOuvrageIdOrderByRankAsc(ouv.getId());
		if(topReserved.isPresent()) {
			Reservation topReserrvationOnTheList = topReserved.get();
			if(!timers.containsKey(topReserrvationOnTheList)) {
				String email = topReserved.get().getUser().getEmail();
				//Mail sender to alert the No.1 user of availability of the book
				EmailTemplateDTO dto = new EmailTemplateDTO(email, ouv.getTitre() + " is available", " This book has become available. " +
						"You have 48 hours to pick it up.");
				utilitiesComponent.send_email(dto);
				//Timer to start 48 hour countdown after user has been alerted of availability
				utilitiesComponent.startTimer(topReserrvationOnTheList);
			}
		}
		this.exemplaireRepository.save(exemplaire);
		this.empruntRepository.deleteById(idEmprunt);
	}

	
}
