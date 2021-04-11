package fr.soro.restcontroller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import fr.soro.Client.EmpruntClient;
import fr.soro.dto.EmpruntDto;
import fr.soro.dto.UserDto;

@RestController
public class EmpruntClientController {
		private EmpruntClient empruntClient;
	
	
	public EmpruntClientController(EmpruntClient empruntService) {
		this.empruntClient = empruntService;
	}

	@GetMapping("/user-emprunts")
	public ModelAndView getUserEmprunts(ModelAndView modelAndView,HttpSession session){
		UserDto user =(UserDto) session.getAttribute("userSession");
		List<EmpruntDto> emprunts = empruntClient.getUserEmprunts(user.getId());
		modelAndView.addObject("emprunts", emprunts);
		modelAndView.setViewName("user-emprunts");
		return modelAndView;
		
	}
	
	@GetMapping("/emprunts-prolongation/{id}")
	public ModelAndView getProlongation(@PathVariable(value = "id") Long id,HttpSession session){		
		this.empruntClient.getProlongation(id);
		UserDto user =(UserDto) session.getAttribute("userSession");
		ModelAndView modelAndView = new ModelAndView("redirect:/user-emprunts/"+user.getId());
		return modelAndView;
		
	}
}
