package fr.soro.restcontroller;


import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import fr.soro.Client.OuvrageClient;
import fr.soro.dto.OuvrageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import fr.soro.SecurityClient;
import fr.soro.Client.UserClient;
import fr.soro.dto.UserDto;




@RestController
public class ConnexionController {
	@Autowired
	private OuvrageClient ouvrageClient;

	private static final String ROLE_USER = "ROLE_USER";
	@Value("${app.serveur.url}")
	private String appUrl;
	private RestTemplate restTemplate;	
	private SecurityClient securityClient;
	private UserClient userClient;
	public ConnexionController(SecurityClient securityClient,RestTemplate restTemplate,UserClient userClient) {
		this.securityClient=securityClient;
		this.restTemplate=restTemplate;
		this.userClient=userClient;
	}


	
	
	@GetMapping(value = "/register") // initialisation du login
	public ModelAndView register(ModelAndView modelAndView) {
		modelAndView.setViewName("register");
		UserDto userDto = new UserDto();
		modelAndView.addObject("userRegister", userDto);
		return modelAndView;
	}
	


	
	@PostMapping(value = "/register") // initialisation du login
	public ModelAndView register(@ModelAttribute("userForm") UserDto userForm, ModelAndView modelAndView,UserDto userDto) throws JsonProcessingException {
		modelAndView.addObject("userRegister", userDto);
		userForm.getRoles().add(ROLE_USER);
		HttpHeaders registrationHeaders = getHeaders();
		String registrationBody = getBody(userForm);
		HttpEntity<String> registrationEntity = new HttpEntity<String>(registrationBody,
				registrationHeaders);
		ResponseEntity<String> registrationResponse = restTemplate.exchange(appUrl+"signup",
				HttpMethod.POST, registrationEntity, String.class);
		if (registrationResponse.getStatusCode().equals(HttpStatus.OK)) {
			modelAndView.setViewName("loginForm");
			return modelAndView;
		}else 
		{
			modelAndView.setViewName("error");
			return modelAndView;
		}
	}
		
//		if (this.securityClient.isUserExisting(userForm.getUsername())){
//			modelAndView.addObject("errormsg", "Mail déjà existant");
//			modelAndView.setViewName("registerView");// Renvoie la page thymleaf
//		}
//		else 
//		modelAndView.addObject("userRegister", userDto);
//		return modelAndView;
	
	
	private String getBody(final UserDto userDto) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(userDto);
	}
	
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}
	
	
	@GetMapping(value = "/login") // initialisation du login
	public ModelAndView loginView(ModelAndView modelAndView) {
		modelAndView.setViewName("loginForm");
		UserDto userDto = new UserDto();
		modelAndView.addObject("userForm", userDto);
		return modelAndView;
	}

	@GetMapping(value = "/infos") // initialisation du login
	public ModelAndView infosUser(ModelAndView modelAndView) {
		modelAndView.setViewName("mesInfosView");
		UserDto userDto = new UserDto();
		return modelAndView;
	}
	
	@PostMapping(value = "/login") 
	public ModelAndView loginPost(ModelAndView modelAndView,@ModelAttribute("userForm")UserDto userForm,HttpSession session) throws JsonProcessingException {
	//	Creation de la requette
		HttpHeaders authenticationHeaders = getHeaders();
		String authenticationBody = getBody(userForm);
		HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody,
				authenticationHeaders);
		//Envoie de la requette
		ResponseEntity<String> authenticationResponse = restTemplate.exchange(appUrl+"auth/signin",
				HttpMethod.POST, authenticationEntity, String.class);
		//Test si la requette est ok
		if (authenticationResponse.getStatusCode().equals(HttpStatus.OK))
		{
		//Stockage du token ds le cache avec la key username
		String username = userForm.getUsername();	
		String token = "Bearer " + authenticationResponse.getBody();
		securityClient.storeToken(token, username);	
		//Recupere l'user et le token pr les passer ds la session
		UserDto userConfirmed = this.userClient.getuserByUsername(username);
		session.setAttribute("tokenSession", token);
		session.setAttribute("userSession", userConfirmed);// On crée une variable de session a l aide de l objet
		List<OuvrageDto> ouvrages = ouvrageClient.getOuvrage();
		modelAndView.addObject("ouvrages", ouvrages);
		modelAndView.setViewName("accueil");
		return modelAndView;
		} else 
		{
			modelAndView.setViewName("error");
			return modelAndView;
		}
	}
	

	// Method pour ce deconecter
		@GetMapping(value = "/logout")
		public ModelAndView logout(HttpSession session) {
			ModelAndView model = new ModelAndView();
			session.setAttribute("userSession", null);// Reatribue null a l userSession pr logout
			session.setAttribute("tokenSession", null);
			List<OuvrageDto> ouvrages = ouvrageClient.getOuvrage();
			model.addObject("ouvrages", ouvrages);
			model.setViewName("/accueil");
			return model;
		}
	
	
	@GetMapping("/users")
	public ModelAndView getusers (ModelAndView modelAndView){		
		ResponseEntity<UserDto[]> response =restTemplate.getForEntity(appUrl+"user", UserDto[].class);
		UserDto[] users = response.getBody();
		List<UserDto> usersDto = Arrays.asList(users);
		modelAndView.addObject("usersForm", usersDto);
		modelAndView.setViewName("listUsers");
		return modelAndView;
		
	}

	
//	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
//	public ModelAndView login(@ModelAttribute("userLogin") User userLogin, HttpSession session) {
//		User userConfirmed = this.userRepository.findByMailAndPassword(userLogin.getMail(), userLogin.getPassword());
//		session.setAttribute("critere", "lieu");
//
//		if (userConfirmed != null) {// On verifie si userConfirm est different de nul pr savoir si l utilisateur
//									// existe
//			session.setAttribute("userSession", userConfirmed);// On crée une variable de session a l aide de l objet
//			ModelAndView model = new ModelAndView("redirect:/home"); // userConfirm qui sera dispo ds toute l appli
//			return model;
//		} else {
//			ModelAndView model = new ModelAndView();
//			model.addObject("errormsg", "Identifiant incorrect !");
//			model.setViewName("loginView");// Renvoie la page thymleaf
//			return model;
//		}
//
//	}
//	
	

}
