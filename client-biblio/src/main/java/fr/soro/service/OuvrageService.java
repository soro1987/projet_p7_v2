package fr.soro.service;

import fr.soro.dto.OuvrageDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.soro.Client.OuvrageClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Service
public class OuvrageService {
	
	private OuvrageClient ouvrageClient;

	
	public OuvrageService(OuvrageClient ouvrageClient) {
		this.ouvrageClient=ouvrageClient;
	}


	public List<OuvrageDto> ouvragesWithImage (){
		List<OuvrageDto> ouvrages = ouvrageClient.getOuvrage();
		List<OuvrageDto> ouvragesWithImage = new ArrayList<>();
		for (OuvrageDto ouvrage : ouvrages) {
			ouvrage.setImage(this.downloadImage(ouvrage.getId()));
			ouvragesWithImage.add(ouvrage);
		}
		return ouvragesWithImage;
	}

	public List<OuvrageDto> removeOuvrageFromList (List<OuvrageDto> initialeList,OuvrageDto ouvrageToRemove){
		/*List<OuvrageDto> ouvrages = ouvrageClient.getOuvrage();
		;*/
		List<OuvrageDto> ouvragesWithoutDouble = new ArrayList<>();

		for (int i = 0; i < initialeList.size(); i++) {
			OuvrageDto ouvrage =initialeList.get(i);
			if (!ouvrageToRemove.getId().equals(ouvrage.getId()))
				ouvragesWithoutDouble.add(ouvrage);
		}
		return ouvragesWithoutDouble;
	}


	public byte[] downloadImage(@PathVariable Long id)
	{
		ResponseEntity<byte[]> downloadedImage = ouvrageClient.downloadImage(id)
				.map(image -> ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; id="+id).body(image))
				.orElseThrow(() -> new IllegalArgumentException("Ouvrage "+id+" not found"));
		return downloadedImage.getBody();
	}



}
