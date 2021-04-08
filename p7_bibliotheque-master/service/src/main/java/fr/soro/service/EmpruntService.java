package fr.soro.service;

import fr.soro.entities.*;
import fr.soro.repositories.*;
import fr.soro.service.job.ReservationJob;
import fr.soro.utilities.UtilitiesComponent;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class EmpruntService {

	private final EmpruntRepository empruntRepository;
	private final UserRepository userRepository;
	private final ExemplaireRepository exemplaireRepository;
	private final OuvrageRepository ouvrageRepository;

	public EmpruntService( EmpruntRepository empruntRepository, UserRepository userRepository,
						   ExemplaireRepository exemplaireRepository, OuvrageRepository ouvrageRepository)
	{
		this.empruntRepository = empruntRepository;
		this.userRepository = userRepository;
		this.exemplaireRepository = exemplaireRepository;
		this.ouvrageRepository = ouvrageRepository;

	}

	public Emprunt get(Long id) {
		return this.empruntRepository.findById(id).get();
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
		setDateEcheance(emprunt);
		Optional<User> user = this.userRepository.findById(idUser);
		emprunt.setUser(user.get());
		Exemplaire exemplaire = this.exemplaireRepository.getExemplaireById(idExmplaire);
		emprunt.getExemplaires().add(exemplaire);
		exemplaire.setDisponible(false);
		Emprunt empruntSaved = this.empruntRepository.save(emprunt);
		user.get().getEmprunts().add(empruntSaved);
		exemplaire.setEmprunt(empruntSaved);
		this.userRepository.save(user.get());
		this.exemplaireRepository.save(exemplaire);
		return empruntSaved;
	}

	public void setDateEcheance(Emprunt emprunt) {
		emprunt.setDateDebut(new Date());
		Calendar calendrier = Calendar.getInstance();
		Date dateCourante = emprunt.getDateDebut();
		calendrier.setTime(dateCourante);
		calendrier.add(Calendar.HOUR, 24*28);
		emprunt.setDateEcheance(calendrier.getTime());
	}

	public List<Emprunt> getUserEmprunt(Long idUser){
		List<Emprunt> userEmprunts =new ArrayList<Emprunt>();
		List<Emprunt> allEmprunts = this.empruntRepository.findAll();
		for (Emprunt emprunt : allEmprunts) {
			if (emprunt.getUser().getId()==idUser) {
				userEmprunts.add(emprunt);
			}
		}return userEmprunts;
	}

	public Emprunt setProlongation(Long id) {
		Emprunt emprunt = this.empruntRepository.findById(id).get();
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
