package fr.soro.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;

import fr.soro.Client.ExemplaireClient;
import fr.soro.Client.OuvrageClient;
import fr.soro.dto.BiblioDto;
import fr.soro.dto.ExemplaireDto;
import fr.soro.dto.OuvrageDto;

@RestController
public class ExemplaireController {
	Long id=(long) 1;
	private ExemplaireClient exemplaireClient;
	private OuvrageClient ouvrageClient;
	
	public ExemplaireController(ExemplaireClient exemplaireService,OuvrageClient ouvrageClient) {
		this.exemplaireClient=exemplaireService;
		this.ouvrageClient=ouvrageClient;
	}

	@GetMapping("/exemplaires-by-biblio")
	public ModelAndView getExBybiblio (ModelAndView modelAndView){		
		List<ExemplaireDto> exemplaires = exemplaireClient.getExemplairesByBiblio(id);
		modelAndView.addObject("exemplaires", exemplaires);
		modelAndView.setViewName("exemplaire-biblio");
		return modelAndView;
	}
	
	@GetMapping("/exemplaires")
	public ModelAndView getExemplaire(ModelAndView modelAndView){		
		List<ExemplaireDto> exemplaires = exemplaireClient.getExemplaires();
		modelAndView.addObject("exemplaires", exemplaires);
		modelAndView.setViewName("accueil");
		for (ExemplaireDto exemplaire : exemplaires) {
		    BiblioDto biblio = exemplaire.getBibliotheque();
		    	System.out.println("================================="+biblio.getNom());
			}

		return modelAndView;
		
	}
	
	
	

	
}
