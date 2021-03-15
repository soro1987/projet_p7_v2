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
	
//	public List<Exemplaire> getAllByBiblio(Bibliotheque biblio ,Long biblioId){
//		return exemplaireRepository.findByIdBibliotheque(biblio,biblioId);
//	}

	public boolean isDisponible(Long idExemplaire){
		Exemplaire exemplaire = this.exemplaireRepository.getOne(idExemplaire);
	if (exemplaire.isDisponible() == false){
		return false;
	}else return true;
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

	public boolean doesUserCurrentlyPossessThisBook(User user, Ouvrage ouvrage){
		/// find exemplaire where ouvrageid is ?
		List<Exemplaire> exemplaires = exemplaireRepository.findAllByOuvrageAndDisponible(ouvrage, false);
		// for every exemplaire found check if its emprunt has a user like ?
		boolean found = false;
		for (Exemplaire exemplaire : exemplaires) {
			if(exemplaire.getEmprunt().getUser().getId() == user.getId()){
				found = true;
				break;
			}
		}
		return found;
	}
}
