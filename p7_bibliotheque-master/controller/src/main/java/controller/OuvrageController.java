package controller;

import fr.soro.entities.Ouvrage;
import fr.soro.repositories.OuvrageRepository;
import fr.soro.service.OuvrageService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
public class OuvrageController {
	

	private OuvrageService ouvrageService;
	private OuvrageRepository ouvrageRepository;


	@PostMapping(value = "/ouvrages/{id}/image")
	@SneakyThrows
	public ResponseEntity<String> uploadImage(@RequestPart("file") MultipartFile file, @PathVariable Long id)
	{
		ouvrageService.uploadImage(file.getBytes(),id);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/ouvrages/")
				.path(id.toString())
				.path("/image")
				.toUriString();
		return ResponseEntity.ok(fileDownloadUri);
	}

	@GetMapping(value = "/ouvrages/{id}/image")
	public ResponseEntity<byte[]> downloadImage(@PathVariable Long id)
	{
		return ouvrageRepository.findById(id)
				.map(ouvrage -> ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; id="+id).body(ouvrage.getImage()))
				.orElseThrow(() -> new IllegalArgumentException("Ouvrage "+id+" not found"));
	}


	
	@RequestMapping(value="/category/{categorie}", method = {RequestMethod.GET})
	public List<Ouvrage> getCategory(@PathVariable(value = "categorie")String categorie){
		return ouvrageService.getByCategorie(categorie);
	}

	@RequestMapping(value="/search/{motcle}", method = {RequestMethod.GET})
	public List<Ouvrage> search(@PathVariable(value = "motcle")String motcle){
		return ouvrageService.getByTitreAuteur(motcle);
	}
	
	
		@PostMapping(value = "/ouvrages")
		@Transactional
		public ResponseEntity<Ouvrage> createOuvrage(@RequestBody Ouvrage ouvrage)
		{
			Ouvrage ouvrageSaved = ouvrageService.save(ouvrage);		
	 		return new ResponseEntity<Ouvrage>(ouvrageSaved, HttpStatus.CREATED);
	 	}
		
		@DeleteMapping(value = "/ouvrages")
		public ResponseEntity<Void> deleteOuvrage(@RequestParam(value = "id", required = true) Long id) {
			
			ouvrageService.delete(id);
			return new ResponseEntity<Void>(HttpStatus.GONE);
	 	}

		
		@PutMapping(value = "/ouvrages")
		public ResponseEntity<Ouvrage> updateOuvrage(@RequestBody Ouvrage ouvrage , @RequestParam(value = "id", required = true) Long id) {
			Ouvrage ouvrageFound = ouvrageService.updated(id, ouvrage);
			return new ResponseEntity<Ouvrage>(ouvrageFound, HttpStatus.OK);
		}
		

		@GetMapping(value = "/ouvrages")
		public ResponseEntity<List<Ouvrage>> getAllOuvrages() {
			List<Ouvrage> ouvrages = ouvrageService.getAll();
			ouvrages.forEach(o-> {o.setNbreExemplaireDispo();});
			return new ResponseEntity<List<Ouvrage>>(ouvrages, HttpStatus.FOUND);
		}
		
		
		@GetMapping(value = "/ouvrages-id/{id}")
		public ResponseEntity<Ouvrage> getOne(@PathVariable(value = "id") Long id) {
			Ouvrage ouvrageFound = ouvrageService.getOne(id);
			return new ResponseEntity<Ouvrage>(ouvrageFound, HttpStatus.FOUND);
		}
		
		@GetMapping(value = "/ouvrages-titre/{titre}")
		public ResponseEntity<List<Ouvrage>> getBytitre(@PathVariable(value = "titre") String titre) {
			List<Ouvrage> ouvrageFound = ouvrageService.getByTitre(titre);
			return new ResponseEntity<List<Ouvrage>>(ouvrageFound, HttpStatus.FOUND);
		}
		
		@GetMapping(value = "/ouvrages-auteur/{auteur}")
		public ResponseEntity<List<Ouvrage>> getByAuteur(@PathVariable(value = "auteur") String auteur) {
			List<Ouvrage> ouvrageFound = ouvrageService.getByAuteur(auteur);
			return new ResponseEntity<List<Ouvrage>>(ouvrageFound, HttpStatus.FOUND);
		}

		
		@GetMapping(value = "/ouvrages/{parution}")
		public ResponseEntity<List<Ouvrage>> getByParution(@PathVariable(value = "parution") Date parution) {
			List<Ouvrage> ouvrageFound = ouvrageService.getByParution(parution);
			return new ResponseEntity<List<Ouvrage>>(ouvrageFound, HttpStatus.FOUND);
		}

}
