package controller;

import java.util.List;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import fr.soro.entities.Bibliotheque;
import fr.soro.service.BibliothequeService;

@RestController
public class BibliothequeController {
	
	@Resource
	BibliothequeService bibliothequeService;
	
	@PostMapping(value = "/bibliotheques")
	@Transactional
	public ResponseEntity<Bibliotheque> createBibliotheque(@RequestBody Bibliotheque bibliotheque)
	{
		Bibliotheque bibliothequeSaved = bibliothequeService.save(bibliotheque);		
 		return new ResponseEntity<Bibliotheque>(bibliothequeSaved, HttpStatus.CREATED);
 	}
	
	@DeleteMapping(value = "/bibliotheques")
	public ResponseEntity<Void> deletebibliotheques(@RequestParam(value = "id", required = true) Long id) {
		
		bibliothequeService.deleted(id);
		return new ResponseEntity<Void>(HttpStatus.GONE);
 	}

	
	@GetMapping(value = "/bibliotheques")
	public ResponseEntity<List<Bibliotheque>> getAllBibliotheque() {
		List<Bibliotheque> bibliotheques = bibliothequeService.getAllBibliotheque();
		return new ResponseEntity<List<Bibliotheque>>(bibliotheques, HttpStatus.FOUND);
	}
	
	
	@GetMapping(value = "/bibliotheques/{id}")
	public ResponseEntity<Bibliotheque> getOneBibliotheques(@PathVariable(value = "id") Long id) {
		Bibliotheque bibliothequeFound = bibliothequeService.getOne(id);
		return new ResponseEntity<Bibliotheque>(bibliothequeFound, HttpStatus.FOUND);
	}
	
	@PutMapping(value = "/bibliotheques")
	public ResponseEntity<Bibliotheque> updateBibliotheque(@RequestBody Bibliotheque bibliotheque , @RequestParam(value = "id", required = true) Long id) {
		Bibliotheque bibliothequeUpdated = bibliothequeService.updated(id, bibliotheque);
		return new ResponseEntity<Bibliotheque>(bibliothequeUpdated, HttpStatus.OK);
	}
}
