package fr.soro.batch;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
      
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import fr.soro.batch.modele.EmpruntDto;


@Component
public class MyMailScheduler{  
	
	@Autowired
	private RestTemplate restTemplate;
    
//    @Scheduled(cron="*/10 * * * * *")
//    public void doSomething() {
////        this.getAllUser();
//    }
    
//	public Emprunt[] getAllUser() {
		
//		  HttpHeaders headers = new HttpHeaders();
//	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//	      HttpEntity <String> entity = new HttpEntity<String>(headers);
	      
//	      ResponseEntity<Emprunt[]> response = restTemplate.exchange("http://localhost:8080/expire-emprunts", HttpMethod.GET, entity, Emprunt[].class);
//	      for(Emprunt emp: response.getBody())  {
//	    	 System.out.println(emp.getUser().getEmail()); 
//	      }
//	      
//	      return response.getBody();
//	}
}