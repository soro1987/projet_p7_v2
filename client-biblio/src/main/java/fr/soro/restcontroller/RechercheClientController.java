package fr.soro.restcontroller;

import java.util.List;
import java.util.Map;

import fr.soro.Client.ReservationClient;
import fr.soro.dto.Category;
import fr.soro.dto.WaitingListCredentialsDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;



import fr.soro.Client.ExemplaireClient;
import fr.soro.Client.OuvrageClient;
import fr.soro.dto.OuvrageDto;

@RestController
public class RechercheClientController {


	private final OuvrageClient ouvrageClient;
	private final ReservationClient reservationClient;

	public RechercheClientController(ExemplaireClient exemplaireClient, OuvrageClient ouvrageClient, ReservationClient reservationClient) {
		this.ouvrageClient = ouvrageClient;
		this.reservationClient = reservationClient;
	}

	@GetMapping("/category")
	public ModelAndView getCategory(ModelAndView modelAndView){
		modelAndView.setViewName("category");
		return modelAndView;

	}
	@RequestMapping(value="/category/{categorie}", method = {RequestMethod.GET})
	public ModelAndView getACategory(@PathVariable String categorie,ModelAndView modelAndView){
		List<OuvrageDto> ouvragesByCategorie = ouvrageClient.getOuvrageByCategory(categorie);
		modelAndView.addObject("ouvragesByCategorie", ouvragesByCategorie);
		modelAndView.setViewName("resultat-category");
		return modelAndView;
	}

	@GetMapping("/recherche")
	public ModelAndView findOuvrage(ModelAndView modelAndView){
		modelAndView.setViewName("recherche-initiale");
		return modelAndView;

	}
	
	@RequestMapping(value="/search", method = {RequestMethod.GET})
	public ModelAndView search(@RequestParam(value = "motcle", defaultValue = "defaultmotcle")String motcle,ModelAndView modelAndView){				
		List<OuvrageDto> ouvragesRechercher = ouvrageClient.getOuvrageBytitredAuteur(motcle);
		modelAndView.addObject("ouvragesRechercher", ouvragesRechercher);
		modelAndView.setViewName("/resultat-recherche");
		return modelAndView;
	}
	
	@GetMapping("/dispo/{ouvrageId}")
	public ModelAndView getOuvrageCountBybibliotheque(@PathVariable(value = "ouvrageId") Long ouvrageId,ModelAndView modelAndView){			
		Map<String, Object> ouvrageCountBybiblio = ouvrageClient.getOuvrageCountBybibliotheque(ouvrageId);
		modelAndView.addObject("ouvrageCountBybiblio", ouvrageCountBybiblio);
		modelAndView.setViewName("disponibilite-ouvrage");
		return modelAndView;
	}
	@GetMapping("/infos-reservation/{ouvrageId}")
	public ModelAndView getOuvrageReservationInfos(@PathVariable(value = "ouvrageId") Long ouvrageId,ModelAndView modelAndView){
		WaitingListCredentialsDto infos = reservationClient.getOuvrageWaitingListCredentials(ouvrageId);
		modelAndView.addObject("infos", infos);
		modelAndView.setViewName("reservation-info");
		return modelAndView;
	}
	
	
}