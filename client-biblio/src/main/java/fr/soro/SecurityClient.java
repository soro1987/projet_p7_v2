package fr.soro;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fr.soro.dto.ExemplaireDto;
import fr.soro.dto.UserDto;
import fr.soro.utils.UserContext;




@Component
public class SecurityClient {
	
	@Value("${app.serveur.url}")
	private String appUrl;
	private CacheManager cacheManager;	
	private UserContext userContext;		
	private RestTemplate restTemplate;
	
	//injection via constructeur
	public SecurityClient(RestTemplate restTemplate,UserContext userContext,CacheManager cacheManager) {
		this.restTemplate = restTemplate;
		this.userContext = userContext;
		this.cacheManager = cacheManager;
	}
	
	//Cache ou je stocke les tokens recuperable avec la clé username
	@Cacheable(cacheNames = "token",key="#username")
	//Methode d'identification qui fait appel au controlleur dans p7
	public String storeToken(String token,String username) {				
	return token;		
	}
	
	@Scheduled(fixedDelay = 1800000)
	@CacheEvict(allEntries = true, cacheNames = "token")
	public void refreshToken() {}
	
	public String getToken(String username) {
		Cache token = this.cacheManager.getCache("token");		
		return token.get(username,String.class);
		
	}
	
//	public boolean isUserExisting(String username){		
//		ResponseEntity<UserDto> response =restTemplate.getForEntity(appUrl+"/user/"+username, UserDto.class);
//		UserDto user = response.getBody();	
//		if (user==null) {
//			return  false;
//		}
//		return true;
//		
//	}

}