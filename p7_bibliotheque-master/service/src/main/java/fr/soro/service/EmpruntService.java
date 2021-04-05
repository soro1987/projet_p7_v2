package fr.soro.service;

import fr.soro.entities.*;
import fr.soro.repositories.*;
import fr.soro.service.job.ReservationJob;
import fr.soro.utilities.UtilitiesComponent;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class EmpruntService {

	private final ReservationJob reservationJob;
	private final EmpruntRepository empruntRepository;
	private final ReservationRepository reservationRepository;
	private final UserRepository userRepository;
	private final ExemplaireRepository exemplaireRepository;
	private final OuvrageRepository ouvrageRepository;
	private final UtilitiesComponent utilitiesComponent;


	public EmpruntService(ReservationJob reservationJob, EmpruntRepository empruntRepository, ReservationRepository reservationRepository,
						  UserRepository userRepository, ExemplaireRepository exemplaireRepository, OuvrageRepository ouvrageRepository,
						  UtilitiesComponent utilitiesComponent)
	{
		this.reservationJob = reservationJob;
		this.empruntRepository = empruntRepository;
		this.reservationRepository = reservationRepository;
		this.userRepository = userRepository;
		this.exemplaireRepository = exemplaireRepository;
		this.ouvrageRepository = ouvrageRepository;
		this.utilitiesComponent = utilitiesComponent;

	}

	public Emprunt get(Long id) {
		return this.empruntRepository.findById(id).get();
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

	public Emprunt save(Long idUser, Long idExmplaire) {
		Emprunt emprunt = new Emprunt();
		emprunt.setDateDebut(new Date());
		Calendar calendrier = Calendar.getInstance();
		Date dateCourante = emprunt.getDateDebut();
		calendrier.setTime(dateCourante);
		calendrier.add(Calendar.HOUR, 24*28);
		emprunt.setDateEcheance(calendrier.getTime());
		Optional<User> user = this.userRepository.findById(idUser);
		emprunt.setUser(user.get());
		Exemplaire exemplaire = this.exemplaireRepository.getExemplaireById(idExmplaire);
		emprunt.getExemplaires().add(exemplaire);
		exemplaire.setDisponible(false);
		Emprunt empruntSaved  = this.empruntRepository.save(emprunt);
		user.get().getEmprunts().add(empruntSaved);
		exemplaire.setEmprunt(empruntSaved);

		this.userRepository.save(user.get());
		this.exemplaireRepository.save(exemplaire);
		return empruntSaved;
	}
	public List<Emprunt> getUserEmprunt(Long idUser){
		List<Emprunt> userEmprunts =new ArrayList<Emprunt>();
		List<Emprunt> allEmprunts = this.getAllEmprunt();
		for (Emprunt emprunt : allEmprunts) {
			if (emprunt.getUser().getId()==idUser) {
				userEmprunts.add(emprunt);
			}
	}return userEmprunts;

	}

	public Emprunt setProlongation(Long id) {
		Emprunt emprunt = this.get(id);
		Date dateEcheanceActuelle = emprunt.getDateEcheance();
		if(!emprunt.isProlongation() && new Date().before(dateEcheanceActuelle)){
			emprunt.setProlongation(true);
			Calendar calendrier = Calendar.getInstance();
			calendrier.setTime(dateEcheanceActuelle);
			calendrier.add(Calendar.HOUR, 24*28);
			emprunt.setDateEcheance(calendrier.getTime());
			this.empruntRepository.save(emprunt);
		}
		return emprunt;
	}





	public Ouvrage retrieveAndUpdateOuvrage(Exemplaire exemplaire) {
		Ouvrage ouv = exemplaire.getOuvrage();
		ouv.increase();
		ouvrageRepository.save(ouv);
		return ouv;
	}

	public Exemplaire resetExemplaire(Long idExmplaire) {
		Exemplaire exemplaire = this.exemplaireRepository.getExemplaireById(idExmplaire);
		exemplaire.setDisponible(true);
		return exemplaire;
	}


	public Date findEmpruntEarliestReturnDate(Long ouvrageId) {
//		Optional<Emprunt> emprunt = empruntRepository.findFirstByExemplaireOuvrageIdOrderByDateEcheanceDesc(ouvrageId);
//		return emprunt.map(Emprunt::getDateEcheance).orElse(null);
		return new Date();
	}



}
