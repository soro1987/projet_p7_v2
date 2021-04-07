package fr.soro.controller;

import fr.soro.dto.OuvrageDto;
import fr.soro.entities.Ouvrage;
import fr.soro.mapper.OuvrageMapper;
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
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class OuvrageController {
	

	private OuvrageService ouvrageService;
	private OuvrageRepository ouvrageRepository;
	private OuvrageMapper ouvrageMapper;



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
	public ResponseEntity<List<OuvrageDto>> getCategory(@PathVariable(value = "categorie")String categorie){
		List<Ouvrage> ouvrages = ouvrageService.getByCategorie(categorie);
		List<OuvrageDto> ouvrageDtos = ouvrages.stream()
				.map(ouvrage -> ouvrageMapper.from(ouvrage))
				.collect(Collectors.toList());
		return new ResponseEntity<List<OuvrageDto>>(ouvrageDtos, HttpStatus.FOUND);
	}

	@RequestMapping(value="/search/{motcle}", method = {RequestMethod.GET})
	public ResponseEntity <List<OuvrageDto>> search(@PathVariable(value = "motcle")String motcle){
		List<Ouvrage> ouvrages = ouvrageService.getByTitreAuteur(motcle);
		List<OuvrageDto> ouvrageDtos = ouvrages.stream()
				.map(ouvrage -> ouvrageMapper.from(ouvrage))
				.collect(Collectors.toList());
		return new ResponseEntity<List<OuvrageDto>>(ouvrageDtos, HttpStatus.FOUND);
	}

		@PostMapping(value = "/ouvrages")
		@Transactional
		public ResponseEntity<OuvrageDto> createOuvrage(@RequestBody Ouvrage ouvrage)
		{
			Ouvrage ouvrageSaved = ouvrageService.save(ouvrage);		
	 		return new ResponseEntity<OuvrageDto>( ouvrageMapper.from(ouvrageSaved), HttpStatus.CREATED);
	 	}
		
		@DeleteMapping(value = "/ouvrages")
		public ResponseEntity<Void> deleteOuvrage(@RequestParam(value = "id", required = true) Long id) {
			ouvrageService.delete(id);
			return new ResponseEntity<Void>(HttpStatus.GONE);
	 	}

		
		@PutMapping(value = "/ouvrages")
		public ResponseEntity<OuvrageDto> updateOuvrage(@RequestBody Ouvrage ouvrage , @RequestParam(value = "id", required = true) Long id) {
			Ouvrage ouvrageFound = ouvrageService.updated(id, ouvrage);
			return new ResponseEntity<OuvrageDto>(ouvrageMapper.from(ouvrageFound), HttpStatus.OK);
		}
		

		@GetMapping(value = "/ouvrages")
		public ResponseEntity<List<OuvrageDto>> getAllOuvrages() {
			List<Ouvrage> ouvrages = ouvrageService.getAll();
			return ResponseEntity.ok(ouvrages.stream().peek(Ouvrage::setNbreExemplaireDispo)
					.map(ouvrage -> ouvrageMapper.from(ouvrage))
					.collect(Collectors.toList()));
		}


	@GetMapping(value = "/ouvrages-id/{id}")
	public ResponseEntity<OuvrageDto> getOne(@PathVariable(value = "id") Long id) {
		Ouvrage ouvrageFound = ouvrageService.getOne(id);
		OuvrageDto ouvrageDto = this.ouvrageMapper.from(ouvrageFound);
		return ResponseEntity.ok(ouvrageDto);
	}

//		@GetMapping(value = "/ouvrages-id/{id}")
//		public ResponseEntity<Ouvrage> getOne(@PathVariable(value = "id") Long id) {
//			Ouvrage ouvrageFound = ouvrageService.getOne(id);
//			return new ResponseEntity<Ouvrage>(ouvrageFound, HttpStatus.FOUND);
//		}

		@GetMapping(value = "/ouvrages-titre/{titre}")
		public ResponseEntity<List<OuvrageDto>> getBytitre(@PathVariable(value = "titre") String titre) {
			List<Ouvrage> ouvrageFound = ouvrageService.getByTitre(titre);
			List<OuvrageDto> ouvrageDtos = ouvrageFound.stream()
					.map(ouvrage -> ouvrageMapper.from(ouvrage))
					.collect(Collectors.toList());
			return new ResponseEntity<List<OuvrageDto>>(ouvrageDtos, HttpStatus.FOUND);
		}

		
		@GetMapping(value = "/ouvrages-auteur/{auteur}")
		public ResponseEntity<List<OuvrageDto>> getByAuteur(@PathVariable(value = "auteur") String auteur) {
			List<Ouvrage> ouvrageFound = ouvrageService.getByAuteur(auteur);
			List<OuvrageDto> ouvrageDtos = ouvrageFound.stream()
					.map(ouvrage -> ouvrageMapper.from(ouvrage))
					.collect(Collectors.toList());
			return new ResponseEntity<List<OuvrageDto>>(ouvrageDtos, HttpStatus.FOUND);
		}

		
		@GetMapping(value = "/ouvrages/{parution}")
		public ResponseEntity<List<OuvrageDto>> getByParution(@PathVariable(value = "parution") Date parution) {
			List<Ouvrage> ouvrageFound = ouvrageService.getByParution(parution);
			List<OuvrageDto> ouvrageDtos = ouvrageFound.stream()
					.map(ouvrage -> ouvrageMapper.from(ouvrage))
					.collect(Collectors.toList());
			return new ResponseEntity<List<OuvrageDto>>(ouvrageDtos, HttpStatus.FOUND);
		}

}
