package controller;

import fr.soro.entities.Emprunt;
import fr.soro.entities.User;
import fr.soro.service.EmpruntService;
import fr.soro.service.ExemplaireService;
import fr.soro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
public class EmpruntController {
	
	@Autowired
	private EmpruntService empruntService;

	@Autowired
	private ExemplaireService exemplaireService;

	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/emprunts/{idUser}/{idExemplaire}")
	public ResponseEntity<Emprunt> createEmprunt(
			@PathVariable(value = "idUser") Long idUser, 
			@PathVariable(value = "idExemplaire") Long idExemplaire, 
			@RequestBody Emprunt emprunt
		)
	{
		if (!exemplaireService.isDisponible(idExemplaire)){
			return new ResponseEntity( HttpStatus.NOT_ACCEPTABLE);
		}
		Emprunt empruntsSaved = empruntService.save(idUser, idExemplaire, emprunt);
 		return new ResponseEntity<Emprunt>(empruntsSaved, HttpStatus.CREATED);
 	}


	@DeleteMapping(value = "/emprunts/delete/{empruntId}/{exemplaireId}")
	public ResponseEntity<Void> deleteEmprunt(@PathVariable(value = "exemplaireId") Long exemplaireId,@PathVariable(value = "empruntId") Long empruntId) {
		empruntService.delete( empruntId,exemplaireId);
		return new ResponseEntity<Void>(HttpStatus.GONE);
 	}

	
	@GetMapping(value = "/emprunts-user/{idUser}")
	public ResponseEntity<List<Emprunt>> getUserEmprunt(@PathVariable(value = "idUser") Long idUser) {
		List<Emprunt> userEmprunts =new ArrayList<Emprunt>();
		List<Emprunt> allEmprunts = empruntService.getAllEmprunt();
		for (Emprunt emprunt : allEmprunts) {
		    if (emprunt.getUser().getId()==idUser) {
		    	userEmprunts.add(emprunt);
			}
		}
		return new ResponseEntity<List<Emprunt>>(userEmprunts, HttpStatus.FOUND);
	}
	
	@GetMapping(value = "/emprunts-user")
	public ResponseEntity<List<Emprunt>> getAllEmprunt() {
		List<Emprunt> empruntsUpdated = empruntService.getAllEmprunt();
		return new ResponseEntity<List<Emprunt>>(empruntsUpdated, HttpStatus.FOUND);
	}
	
	@GetMapping(value = "/emprunts/expired")
	public ResponseEntity<List<Emprunt>> getExpireEmprunt() {
		List<Emprunt> empruntsExpire = empruntService.getAllExpireEmprunt();
		return new ResponseEntity<List<Emprunt>>(empruntsExpire, HttpStatus.FOUND);
	}
	
	@GetMapping(value = "/emprunts/user/expired")
	public ResponseEntity<List<User>> getUsersEmpruntExpire() {
		List<Emprunt> empruntsExpire = empruntService.getAllExpireEmprunt();
		List<User> empruntsExpireUsers = userService.getUsersEmpruntExpire(empruntsExpire);
		return new ResponseEntity<List<User>>(empruntsExpireUsers, HttpStatus.FOUND);
	}
	
	
	@GetMapping(value = "/emprunts/{id}")
	public ResponseEntity<Emprunt> getOneEmprunt(@PathVariable(value = "id") Long id) {
		Emprunt empruntsUpdated = empruntService.get(id);
		return new ResponseEntity<Emprunt>(empruntsUpdated, HttpStatus.FOUND);
	}
	
	@GetMapping(value = "/emprunts/{datedebut}")
	public ResponseEntity<List<Emprunt>> getDateDebut(@PathVariable(value = "datedebut") Date datedebut) {
		List<Emprunt> empruntsFound = empruntService.getDateDebut(datedebut);
		return new ResponseEntity<List<Emprunt>>(empruntsFound, HttpStatus.FOUND);
	}
	
	@GetMapping(value = "/emprunts/{dateEcheance}")
	public ResponseEntity<List<Emprunt>> getDateEcheance(@PathVariable(value = "dateEcheance") Date dateEcheance) {
		List<Emprunt> empruntsFound = empruntService.getDateEcheance(dateEcheance);
		return new ResponseEntity<List<Emprunt>>(empruntsFound, HttpStatus.FOUND);
	}
	
//	@PutMapping(value = "/employees/{id}")
//	public ResponseEntity<EmployeeVO> updateEmployee(@PathVariable("id") int id
//	                ,EmployeeVO employee) 
//	{
//	    //TODO: Save employee details
//	    return new ResponseEntity<EmployeeVO>(employee, HttpStatus.OK);
//	}
	
	@PutMapping(value = "/emprunts/prolongation/{idEmprunt}")
	public ResponseEntity<Emprunt> getProlongation(@PathVariable(value = "idEmprunt") Long idEmprunt) {
		Emprunt empruntsFound = empruntService.setProlongation(idEmprunt);
		return new ResponseEntity<Emprunt>(empruntsFound, HttpStatus.FOUND);
	}
	
	@GetMapping(value = "/emprunts/{depassement}")
	public ResponseEntity<List<Emprunt>> getDepassement(@PathVariable(value = "depassement") int depassement) {
		List<Emprunt> empruntsFound = empruntService.getDepassement(depassement);
		return new ResponseEntity<List<Emprunt>>(empruntsFound, HttpStatus.FOUND);
	}

}
