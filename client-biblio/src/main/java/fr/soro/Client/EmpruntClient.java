package fr.soro.Client;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.client.RestTemplate;
import fr.soro.dto.EmpruntDto;
@Service
public class EmpruntClient {
	
	@Value("${app.serveur.url}")
	private String appUrl;
	@Autowired
	private RestTemplate securedRestTemplate;

	public void getProlongation(Long empruntId){
		securedRestTemplate.put(appUrl+"/emprunts/prolongation/"+empruntId, EmpruntDto.class);
		
	}

	public List<EmpruntDto> getUserEmprunts(Long userId){		
		ResponseEntity<EmpruntDto[]> response =securedRestTemplate.getForEntity(appUrl+"/emprunts-user/"+userId, EmpruntDto[].class);
		EmpruntDto[] emprunt = response.getBody();
		List<EmpruntDto> userEmprunts = Arrays.asList(emprunt);
		return userEmprunts;		
	}
	
	public List<EmpruntDto> getEmprunts(){		
		ResponseEntity<EmpruntDto[]> response =securedRestTemplate.getForEntity(appUrl+"/emprunts", EmpruntDto[].class);
		EmpruntDto[] emprunt = response.getBody();
		List<EmpruntDto> empruntDto = Arrays.asList(emprunt);
		return empruntDto;		
	}
	
	public EmpruntDto getEmpruntDtoById(Long empruntId){		
		ResponseEntity<EmpruntDto> response =securedRestTemplate.getForEntity(appUrl+"/emprunts/"+empruntId, EmpruntDto.class);
		EmpruntDto emprunt = response.getBody();	
		return emprunt;		
	}

}
