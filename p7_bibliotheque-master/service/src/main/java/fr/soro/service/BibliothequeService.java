package fr.soro.service;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import fr.soro.entities.Bibliotheque;
import fr.soro.repositories.BibliothequeRepository;

@Service
public class BibliothequeService {
	
	@Resource
	private BibliothequeRepository bibliothequeRepository;
	
	public Bibliotheque getOne(Long id) {
		return this.bibliothequeRepository.getOne(id);
	}
	
	public List<Bibliotheque> getNom(String nom) {
		return this.bibliothequeRepository.findByNom(nom);
	}
	
	public Bibliotheque updated(Long id, Bibliotheque bibliotheque) {
		Bibliotheque bibliothequeUpdated = this.getOne(id);
		
		bibliothequeUpdated.setNom(bibliotheque.getNom());
		bibliothequeUpdated.setAdresse(bibliotheque.getAdresse());
		bibliothequeUpdated.setDescription(bibliotheque.getDescription());
		bibliothequeUpdated.setEmail(bibliotheque.getEmail());
		bibliothequeUpdated.setTelephone(bibliotheque.getTelephone());
		
		return bibliothequeUpdated;
	}

	public void deleted(Long id) {
		this.bibliothequeRepository.deleteById(id);
	}

	public List<Bibliotheque> getAllBibliotheque() {
		return this.bibliothequeRepository.findAll();
	}

	public Bibliotheque save(Bibliotheque bibliotheque) {
		return this.bibliothequeRepository.save(bibliotheque);
	}
}