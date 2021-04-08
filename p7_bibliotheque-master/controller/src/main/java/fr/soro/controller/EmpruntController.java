package fr.soro.controller;

import fr.soro.dto.EmpruntDto;
import fr.soro.entities.Emprunt;
import fr.soro.entities.User;
import fr.soro.mapper.EmpruntMapper;
import fr.soro.service.EmpruntService;
import fr.soro.service.ExemplaireService;
import fr.soro.service.ReservationService;
import fr.soro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
public class EmpruntController {

	@Autowired
	private EmpruntMapper empruntMapper;

	@Autowired
	private EmpruntService empruntService;

	@Autowired
	private ExemplaireService exemplaireService;

	@Autowired
	private UserService userService;

	@Autowired
	private ReservationService reservationService;

	@PostMapping(value = "/emprunts/{idUser}/{idExemplaire}")
	public ResponseEntity<Emprunt> createEmprunt(@PathVariable Long idUser,@PathVariable Long idExemplaire){
		if (!exemplaireService.isDisponible(idExemplaire)){
			return ResponseEntity.badRequest().build();
		}
		Emprunt empruntsSaved = empruntService.save(idUser, idExemplaire);
		return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/emprunts/").path(empruntsSaved.getId().toString()).build().toUri()).build();
 	}

	@DeleteMapping(value = "/emprunts/delete/{empruntId}/{exemplaireId}")
	public ResponseEntity<Void> deleteEmprunt(@PathVariable(value = "exemplaireId") Long exemplaireId,@PathVariable(value = "empruntId") Long empruntId) {
		reservationService.returnEmprunt( empruntId,exemplaireId);
		return new ResponseEntity<Void>(HttpStatus.GONE);
 	}

	@GetMapping(value = "/emprunts-user/{idUser}")
	public ResponseEntity<List<EmpruntDto>> getUserEmprunt(@PathVariable(value = "idUser") Long idUser) {
		List<Emprunt> userEmprunts =empruntService.getUserEmprunt(idUser);
		List<EmpruntDto> userEmpruntsDto= userEmprunts
				.stream()
				.map(emprunt -> empruntMapper.from(emprunt))
				.collect(Collectors.toList());
		System.out.println(userEmpruntsDto);
		return new ResponseEntity<List<EmpruntDto>>(userEmpruntsDto, HttpStatus.FOUND);
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

	@PutMapping(value = "/emprunts/prolongation/{idEmprunt}")
	public ResponseEntity<Void> setProlongation(@PathVariable Long idEmprunt) {
		empruntService.setProlongation(idEmprunt);
		return  ResponseEntity.ok().build();
	}


}
