package fr.soro.controller;

import fr.soro.entities.Bibliotheque;
import fr.soro.entities.Exemplaire;
import fr.soro.service.ExemplaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ExemplaireController {

	@Autowired
	private ExemplaireService exemplaireService;

	@PostMapping(value = "/exemplaires/{idOuvrage}/{idBiblio}")
	@Transactional
	public ResponseEntity<Exemplaire> createExemplaire(@PathVariable(value = "idOuvrage") Long idOuvrage, @PathVariable(value = "idBiblio") Long idBiblio) {
		Exemplaire exemplaireSaved = exemplaireService.save(idOuvrage, idBiblio);
		return new ResponseEntity<Exemplaire>(exemplaireSaved, HttpStatus.CREATED);
	}
	

	@DeleteMapping(value = "/exemplaires/{id}")
	public ResponseEntity<Void> deleteExemplaire(@PathVariable(value = "id", required = true) Long id) {
		exemplaireService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.GONE);
	}

	@GetMapping("/ouvrages/exemplairecount/{id}")
	public Map<String, Object> getOuvrageCountBybibliotheque(@PathVariable(value = "id") Long ouvrageId){
		return exemplaireService.getExempleCountByBibliotheque(ouvrageId);
}


}
