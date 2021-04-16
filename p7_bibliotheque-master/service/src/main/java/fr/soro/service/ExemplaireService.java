package fr.soro.service;

import fr.soro.entities.*;
import fr.soro.repositories.BibliothequeRepository;
import fr.soro.repositories.ExemplaireRepository;
import fr.soro.repositories.OuvrageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExemplaireService {
	
	@Resource
	private ExemplaireRepository exemplaireRepository;
	@Autowired
	private BibliothequeRepository bibliothequeRepository;
	@Autowired
	private OuvrageRepository ouvrageRepository;
	
	
	public List<Exemplaire> getAll(){
		return exemplaireRepository.findAll();
	}

	public boolean isDisponible(Long idExemplaire){
		Optional<Exemplaire> exemplaire = this.exemplaireRepository.findById(idExemplaire);
		return exemplaire.map(Exemplaire::isDisponible).orElse(false);
	}

	public void delete(Long id) {
		this.exemplaireRepository.deleteById(id);
	}
	
	public Exemplaire save(Long idOuvrage, Long idBiblio) {
		Exemplaire exemplaire = new Exemplaire();
		Ouvrage ouvrage = this.ouvrageRepository.getOne(idOuvrage);
		Bibliotheque bibliotheque = this.bibliothequeRepository.getOne(idBiblio);
		exemplaire.setDisponible(true);
		exemplaire.setBibliotheque(bibliotheque);
		exemplaire.setOuvrage(ouvrage);
		Exemplaire exemplaireSaved = this.exemplaireRepository.save(exemplaire);
		ouvrage.getExemplaires().add(exemplaireSaved);
		bibliotheque.getExemplaires().add(exemplaireSaved);
		this.bibliothequeRepository.save(bibliotheque);
		this.ouvrageRepository.save(ouvrage);
		return exemplaireSaved;
	}

	public Exemplaire getOneById(Long id) {
		
		return this.exemplaireRepository.getOne(id);
	}

	
	public Map<String, Object> getExempleCountByBibliotheque(Long ouvrageId){
	return	this.bibliothequeRepository.findAll()
		.stream()
		.collect(Collectors.toMap(Bibliotheque::getNom, biblio ->			
			this.exemplaireRepository.countByOuvrageIdAndBibliothequeNomAndDisponibleTrue(ouvrageId, biblio.getNom())			
		 ));
		
		
	}


}
