package fr.soro.service;

import fr.soro.entities.*;
import fr.soro.repositories.EmpruntRepository;
import fr.soro.repositories.ExemplaireRepository;
import fr.soro.repositories.ReservationRepository;
import fr.soro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.Trigger;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class EmpruntService {
	@Autowired
	private EmpruntRepository empruntRepository;

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ExemplaireRepository exemplaireRepository;

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

	public void delete(Long idEmprunt,Long idExmplaire) {
		Exemplaire exemplaire = this.exemplaireRepository.getExemplaireById(idExmplaire);
		exemplaire.setEmprunt(null);
		exemplaire.setDisponible(true);
		// check if there are any reservation made for the ouvrage returned
		//Trigger  runs when book is returned

		Ouvrage ouv = exemplaire.getOuvrage();
		Optional<Reservation> topReserved =
				reservationRepository.findTopByOuvrageIdOrderByRankAsc(ouv.getId());
		//Mail sender to alert the No.1 user of availability of the book
		EmailTemplate
		//Timer to start 48 hour countdown after user has been alerted of availability
		this.exemplaireRepository.save(exemplaire);
		this.empruntRepository.deleteById(idEmprunt);
	}
		 
	
}
