package fr.soro.restcontroller;

import fr.soro.Client.OuvrageClient;
import fr.soro.dto.OuvrageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class AcceuilController {

    @Autowired
	private OuvrageClient ouvrageClient;

	@GetMapping(value ="/accueil")
	public ModelAndView getAccueil(ModelAndView modelAndView) {
		List<OuvrageDto> ouvrages = ouvrageClient.getOuvrage();
		modelAndView.addObject("ouvrages", ouvrages);
		modelAndView.setViewName("accueil");
		return modelAndView;
	}
	
}
