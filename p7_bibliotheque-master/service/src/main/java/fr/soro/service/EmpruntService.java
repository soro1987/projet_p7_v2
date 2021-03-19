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
	private final EarliestReturnDateService earliestReturnDateService;
	private final ReservationService reservationService;

	public EmpruntService(ReservationJob reservationJob, EmpruntRepository empruntRepository, ReservationRepository reservationRepository,
						  UserRepository userRepository, ExemplaireRepository exemplaireRepository, OuvrageRepository ouvrageRepository,
						  UtilitiesComponent utilitiesComponent, EarliestReturnDateService earliestReturnDateService, ReservationService reservationService)
	{
		this.reservationJob = reservationJob;
		this.empruntRepository = empruntRepository;
		this.reservationRepository = reservationRepository;
		this.userRepository = userRepository;
		this.exemplaireRepository = exemplaireRepository;
		this.ouvrageRepository = ouvrageRepository;
		this.utilitiesComponent = utilitiesComponent;
		this.earliestReturnDateService = earliestReturnDateService;
		this.reservationService = reservationService;
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
		exemplaire.getOuvrage().setNbreExemplaireDispo(exemplaire.getOuvrage().getNbreExemplaireDispo() -1);
		Emprunt empruntSaved  = this.empruntRepository.save(emprunt);
		user.getEmprunts().add(empruntSaved);
		exemplaire.setEmprunt(empruntSaved);
		
		this.userRepository.save(user);
		this.ouvrageRepository.saveAndFlush(exemplaire.getOuvrage());
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
		Exemplaire exemplaire = this.resetExemplaire(idExmplaire);
		Ouvrage ouv = this.retrieveAndUpdateOuvrage(exemplaire);
		//Persist the changes in db
		this.exemplaireRepository.save(exemplaire);
		this.empruntRepository.deleteById(idEmprunt);
		//If there is a reservation for this ouvrage send mail to first reservation in line
		this.reservationService.sendMailToPrioritaryReservationWhenOuvrageIsDisponible(ouv);
	}



	private Ouvrage retrieveAndUpdateOuvrage(Exemplaire exemplaire) {
		Ouvrage ouv = exemplaire.getOuvrage();
		ouv.increase();
		ouvrageRepository.save(ouv);
		return ouv;
	}

	private Exemplaire resetExemplaire(Long idExmplaire) {
		Exemplaire exemplaire = this.exemplaireRepository.getExemplaireById(idExmplaire);
		exemplaire.setEmprunt(null);
		exemplaire.setDisponible(true);
		return exemplaire;
	}


	public Date findEmpruntEarliestReturnDate(Long ouvrageId) {
		return empruntRepository.findFirstByOuvrageIdOrderByDateEcheanceDesc(ouvrageId).getDateEcheance();
	}


	public boolean canBeBooked(Long ouvrageId) {
		//Retrieve number of exemplaires and the number of reservation available
		Optional<Ouvrage> ouvrage = ouvrageRepository.findById(ouvrageId);
		Optional<Long> numberOfReservationForTheBook = reservationService.numberOfReservationForTheBook(ouvrageId);
		//Check if the number of reservation is less then two time the number of exemplaires
		if (numberOfReservationForTheBook.isPresent() ){
			if (ouvrage.get().getNbreExemplaireDispo() *2 < numberOfReservationForTheBook.get()){
				return true;
			}
		}
		return false;
	}
}
