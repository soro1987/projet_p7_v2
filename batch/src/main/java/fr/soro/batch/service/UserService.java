package fr.soro.batch.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fr.soro.batch.client.EmpruntClient;
import fr.soro.batch.modele.EmpruntDto;
import fr.soro.batch.modele.UserDto;


@Service
public class UserService {
	
	
	@Autowired
	private EmpruntClient empruntClient;
	
	


	public List<UserDto> getUsersMailsExpired(){
		List<EmpruntDto> empruntsExpire = empruntClient.getExpireEmprunts();
		List<UserDto> usersMailsExpired = new ArrayList<UserDto>();

		for(EmpruntDto emprunt : empruntsExpire) 
		{
			UserDto userMailsExpired = emprunt.getUser();
			System.out.println("============================================================================="+emprunt.getUser().getEmail());
			System.out.println("============================================================================="+userMailsExpired);
			usersMailsExpired.add(userMailsExpired);
		}
		return usersMailsExpired;
	
	
	}

}
