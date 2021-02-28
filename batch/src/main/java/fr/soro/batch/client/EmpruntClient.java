package fr.soro.batch.client;

	import java.util.Arrays;
	import java.util.List;

	import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

	import fr.soro.batch.modele.EmpruntDto;
import fr.soro.batch.modele.UserDto;


@Service

	public class EmpruntClient {
		private static final String EXPIRE_USER_EMPRUNT_URL = "http://localhost:8080/emprunts/user/expired";
		private static final String EXPIRE_EMPRUNT_URL = "http://localhost:8080/emprunts/expired";

		private RestTemplate restTemplate;
		
		public EmpruntClient(RestTemplate restTemplate) {
			this.restTemplate = restTemplate;
		}

		public List<UserDto> getUserExpireEmprunts(){		
			ResponseEntity<UserDto[]> response =restTemplate.getForEntity(EXPIRE_USER_EMPRUNT_URL, UserDto[].class);
			UserDto[] userExpireEmprunt = response.getBody();
			List<UserDto> userExpireEmprunts= Arrays.asList(userExpireEmprunt);
			return userExpireEmprunts;		
		}
		
		public List<EmpruntDto> getExpireEmprunts(){		
			ResponseEntity<EmpruntDto[]> response =restTemplate.getForEntity(EXPIRE_EMPRUNT_URL, EmpruntDto[].class);
			EmpruntDto[] empruntExpire = response.getBody();
			List<EmpruntDto> empruntExpires= Arrays.asList(empruntExpire);
			return empruntExpires;		
		}

	}