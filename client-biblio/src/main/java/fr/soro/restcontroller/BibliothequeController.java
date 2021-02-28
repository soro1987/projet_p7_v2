package fr.soro.restcontroller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import fr.soro.dto.OuvrageDto;

@RestController
public class BibliothequeController {

	@GetMapping("/contact")
	public ModelAndView ouvrages (ModelAndView modelAndView){		
		modelAndView.setViewName("contact");
		return modelAndView;
	}
}