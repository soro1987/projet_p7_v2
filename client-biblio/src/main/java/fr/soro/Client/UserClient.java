package fr.soro.Client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fr.soro.dto.OuvrageDto;
import fr.soro.dto.UserDto;

@Service
public class UserClient {

	@Value("${app.serveur.url}")
	private String appUrl;
	private RestTemplate restTemplate;
	
	public UserClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	
	public UserDto getuserByUsername(String username){		
		ResponseEntity<UserDto> response =restTemplate.getForEntity(appUrl+"/user/"+username, UserDto.class);
		UserDto userByUsername = response.getBody();	
		return userByUsername;
		
	}
}
