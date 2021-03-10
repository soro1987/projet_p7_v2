package fr.soro.Client;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fr.soro.dto.OuvrageDto;
@Service
public class OuvrageClient {
	
	@Value("${app.serveur.url}")
	private String appUrl;
	@Autowired
	private RestTemplate restTemplate;
    @Autowired
	private RestTemplate securedRestTemplate;
	
/*	public OuvrageClient(RestTemplate restTemplate,RestTemplate securedRestTemplate) {
		this.restTemplate = restTemplate;
		this.securedRestTemplate = securedRestTemplate;
	}*/
	public OuvrageClient( RestTemplate restTemplate, RestTemplate securedRestTemplate) {

		this.restTemplate = restTemplate;
		this.securedRestTemplate = securedRestTemplate;
	}

	public Optional<byte[]> downloadImage(Long id){
		ResponseEntity<byte[]> entity = restTemplate.getForEntity(appUrl + "ouvrages/{id}/image", byte[].class, id);
		return Optional.of(entity.getBody());

	}


	public List<OuvrageDto> getOuvrageBytitredAuteur(String motcle){		
		ResponseEntity<OuvrageDto[]> response =restTemplate.getForEntity(appUrl+"/search/"+motcle, OuvrageDto[].class);
		OuvrageDto[] ouvrage = response.getBody();
		List<OuvrageDto> ouvrageDto = Arrays.asList(ouvrage);
		return ouvrageDto;
		
	}

	public List<OuvrageDto> getOuvrageByCategory(String categorie){
		ResponseEntity<OuvrageDto[]> response =restTemplate.getForEntity(appUrl+"/category/"+categorie, OuvrageDto[].class);
		OuvrageDto[] ouvrage = response.getBody();
		List<OuvrageDto> ouvrageDto = Arrays.asList(ouvrage);
		return ouvrageDto;

	}
	
	public List<OuvrageDto> getOuvrage(){		
		ResponseEntity<OuvrageDto[]> response =securedRestTemplate.getForEntity(appUrl+"/ouvrages", OuvrageDto[].class);
		OuvrageDto[] ouvrage = response.getBody();

		List<OuvrageDto> ouvrageDto = Arrays.asList(ouvrage);
		System.out.println("======================================================="+ouvrageDto.get(1).getImageUrl()+"======================================================="+ouvrageDto.get(1).getTitre());
		return ouvrageDto;
		
	}
	
	public OuvrageDto getOuvrageById(Long ouvrageId){		
		ResponseEntity<OuvrageDto> response =securedRestTemplate.getForEntity(appUrl+"/ouvrages-id/"+ouvrageId, OuvrageDto.class);
		OuvrageDto ouvrage = response.getBody();	
		return ouvrage;
		
	}
	
	public OuvrageDto getOuvrageByTitre(String ouvrageTitre){		
		ResponseEntity<OuvrageDto> response =securedRestTemplate.getForEntity(appUrl+"/ouvrages-titre/"+ouvrageTitre, OuvrageDto.class);
		OuvrageDto ouvrageByTitre = response.getBody();	
		return ouvrageByTitre;
		
	}
	

	public Map<String, Object> getOuvrageCountBybibliotheque(Long ouvrageId){
	return restTemplate.getForObject(appUrl+"/ouvrages/exemplairecount/"+ouvrageId, Map.class);
	    
}


}
