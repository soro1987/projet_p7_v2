package fr.soro.service;

import fr.soro.entities.Emprunt;
import fr.soro.entities.Exemplaire;
import fr.soro.entities.User;
import fr.soro.repositories.EmpruntRepository;
import fr.soro.repositories.ExemplaireRepository;
import fr.soro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



@Service
public class EmpruntService {
	@Autowired
	private EmpruntRepository empruntRepository;
	
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
		this.exemplaireRepository.save(exemplaire);
		this.empruntRepository.deleteById(idEmprunt);
	}
		 
	
}
