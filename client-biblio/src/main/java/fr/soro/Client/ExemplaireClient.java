package fr.soro.Client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fr.soro.dto.EmpruntDto;
import fr.soro.dto.ExemplaireDto;
@Service
public class ExemplaireClient {
	
	@Value("${app.serveur.url}")
	private String appUrl;
	private ExemplaireClient exemplaireService;
	private RestTemplate restTemplate;
	
	public ExemplaireClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public List<ExemplaireDto> getExemplaires(){		
		ResponseEntity<ExemplaireDto[]> response =restTemplate.getForEntity(appUrl+"/exemplaires", ExemplaireDto[].class);
		ExemplaireDto[] exemplaire = response.getBody();
		List<ExemplaireDto> exemplaireDto = Arrays.asList(exemplaire);
		return exemplaireDto;
		
	}
	
	public List<ExemplaireDto> getExemplairesByBiblio(Long biblioId){		
		ResponseEntity<ExemplaireDto[]> response =restTemplate.getForEntity(appUrl+"/exemplaires-biblio/"+biblioId, ExemplaireDto[].class);
		ExemplaireDto[] exemplaires = response.getBody();
		List<ExemplaireDto> exemplairesByBiblio = Arrays.asList(exemplaires);
		return exemplairesByBiblio;
		
	}


}
