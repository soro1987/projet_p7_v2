//package fr.soro.batch.service;
//
//import java.util.Arrays;
//import java.util.Iterator;
//import java.util.Properties;
//import java.util.Set;
//
//import org.apache.logging.log4j.Logger;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.multipart.MultipartFile;
//
//import fr.soro.batch.modele.EmailTemplate;
//import lombok.extern.slf4j.Slf4j;
//@CrossOrigin("*")
//@RestController
//@Slf4j
//public class TextEmailController {
//	
//	//Logger log;
//	
//	@Autowired
//	private EmailService emailService;
//
//	@PostMapping(value="/textemail", consumes = "application/json", produces = "application/json")
//	public EmailTemplate sendEmail(@RequestBody EmailTemplate emailTemplate) {
//		try {
//			//log.info("Envoyer un Simple Text par Email....");
//			emailService.sendTextEmail(emailTemplate);
//			//return "L'email envoyé avec succès!";
//		} catch (Exception ex) {
//			System.out.println("Erreur d'envoie de mail: " + ex);
//		}
//		
//		return emailTemplate;
//	}
//	
//	
//	@PostMapping(value="/attachemail",consumes = "multipart/form-data")
//	public String sendEmailWithAttachment(@RequestPart(value = "file") MultipartFile file, @RequestPart(value = "mail") String mail) {
//		try {
//			//log.info("Envoyer un Fichier par Email....");
//			emailService.sendEmailWithAttachment(file, mail);
//			return "L'email envoyé avec succès!";
//		} catch (Exception ex) {
//			return "Erreur d'envoie de mail: " + ex;
//		}
//	}
//	
//
//}
