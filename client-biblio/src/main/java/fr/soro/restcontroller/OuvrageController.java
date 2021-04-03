package fr.soro.restcontroller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import fr.soro.service.OuvrageService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import fr.soro.Client.OuvrageClient;
import fr.soro.dto.OuvrageDto;

import javax.servlet.http.HttpServletResponse;

@RestController
public class OuvrageController {

	private OuvrageClient ouvrageClient;
	private OuvrageService ouvrageService;
	
	
	public OuvrageController(OuvrageClient ouvrageClient,OuvrageService ouvrageService) {
		this.ouvrageClient = ouvrageClient;
		this.ouvrageService = ouvrageService;
	}

	@GetMapping("/ouvrages")
	public ModelAndView ouvrages (ModelAndView modelAndView){
	/*	List<OuvrageDto> ouvrages;
		ouvrages = ouvrageService.ouvragesWithImage();*/

		List<OuvrageDto> ouvrages = ouvrageClient.getOuvrage();

		modelAndView.addObject("ouvrages", ouvrages);
		modelAndView.setViewName("tous");
		return modelAndView;	
	}
	
	@GetMapping("/ouvrages/{id}")
	public ModelAndView ouvrage (@PathVariable(value = "id") Long id,ModelAndView modelAndView){
		OuvrageDto ouvrage = ouvrageClient.getOuvrageById(id);
		List<OuvrageDto> ouvragesSameCategory = ouvrageClient.getOuvrageByCategory(ouvrage.getCategorie());
		List<OuvrageDto> ouvragesSameCategoryWithoutOuvrage =ouvrageService.removeOuvrageFromList(ouvragesSameCategory,ouvrage);
		modelAndView.addObject("ouvragesSameCategory", ouvragesSameCategoryWithoutOuvrage);
		modelAndView.addObject("ouvrage", ouvrage);
		modelAndView.setViewName("ouvrage_id");
		return modelAndView;
		
	}

	@GetMapping("/ouvrage/image/{id}")
	public void showProductImage(@PathVariable Long id,
                               HttpServletResponse response) throws IOException {
		response.setContentType("image/jpeg"); // Or whatever format you wanna use

		OuvrageDto ouvrage = ouvrageClient.getOuvrageById(id);

		InputStream is = new ByteArrayInputStream(ouvrage.getImage());
		IOUtils.copy(is, response.getOutputStream());
	}


	@GetMapping(value = "/ouvrages/{id}/image")
	public ResponseEntity<byte[]> downloadImage(@PathVariable Long id)
	{
		return ouvrageClient.downloadImage(id)
				.map(image -> ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; id="+id).body(image))
				.orElseThrow(() -> new IllegalArgumentException("Ouvrage "+id+" not found"));
	}
}
