package fr.soro.batch.service;


import java.util.List;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import fr.soro.batch.client.EmpruntClient;
import fr.soro.batch.modele.EmailTemplate;
import fr.soro.batch.modele.EmpruntDto;
import fr.soro.batch.modele.UserDto;






@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;	
	@Autowired
	private UserService userService;	
	@Autowired
	RestTemplate restTemplate = new RestTemplate();
	@Value("${email.address}")
	private String attchEmailAddr;	
	@Autowired
	private EmailService emailService ;	
	@Autowired
	private EmpruntClient empruntClient ;
	
	/*
	 * Methode pour generer les mails
	 */
//	@Scheduled(cron = "0 0 0 * * *")Pour relance tous les jours 
	@Scheduled(cron="*/10 * * * * *")//	Pour test toutes les 10 secondes 
	public void emailInitializer() {
		EmailTemplate mailExp = new EmailTemplate();	
		List<UserDto> usersMailsExpired = empruntClient.getUserExpireEmprunts();
		List<EmpruntDto> empruntExpired = empruntClient.getExpireEmprunts();

		for(UserDto user : usersMailsExpired) 		
		{
			for(EmpruntDto emprunt : empruntExpired) 
			{
				System.out.println("=================================Email-Send-Console-Log===================================");
				mailExp.setSubject("Expiration de l'emprunt");
				if (emprunt.isProlongation()==true) {
					mailExp.setBody("Bonjour "+user.getNom()+",vous deviez rendre votre emprunt numeroter : "+emprunt.getId()+",en date du "+emprunt.getDateEcheance()+". Merci de les ramenés dans les plus bref delai. Sous peine de poursuite !");
                }else {
					mailExp.setBody("Bonjour "+user.getNom()+",vous deviez rendre votre emprunt numeroter : "+emprunt.getId()+",en date du "+emprunt.getDateEcheance()+". Vous avez encore la possibiliter de le prolonger en vous connectant dans votre espace personnel sur http://localhost:8080/login. Cordialement!");
				}
				mailExp.setSendTo(user.getEmail());
				emailService.sendTextEmail(mailExp);
		    }
		}
	}	
		
	
	/*
	 * Methode d'envoie des mails
	 */	
	public void setEmailAddress(String emailToSend)
	{
		//this.attchEmailAddr += emailToSend;
		this.attchEmailAddr = this.attchEmailAddr + emailToSend;		
	}
	public void sendTextEmail(EmailTemplate emailTemplate) {		
		this.setEmailAddress(emailTemplate.getSendTo());
		SimpleMailMessage msg = new SimpleMailMessage();
		try {
			if (emailTemplate.getSendTo().contains(",")) {
				String[] emails = emailTemplate.getSendTo().split(",");
				int receipantSize = emails.length;
				for (int i = 0; i < receipantSize; i++) {
					msg.setTo(emails[i]);
					msg.setSubject(emailTemplate.getSubject());
					msg.setText(emailTemplate.getBody());
					javaMailSender.send(msg);
				}
			} else {
				msg.setTo(emailTemplate.getSendTo());
				msg.setSubject(emailTemplate.getSubject());
				msg.setText(emailTemplate.getBody());
				javaMailSender.send(msg);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}		



















		
		/*this.empruntRepository.findAll().forEach(emp -> {
			
			String ouvrages = "";
			
			if(currentDate.getDate() >= emp.getDateEcheance().getDate() && currentDate.getMonth() == emp.getDateEcheance().getMonth() && emp.isRendu() == false) {
				
				if(emp.getExemplaires() != null) {
					for(int i =0; i < emp.getExemplaires().size(); i++) {
						ouvrages  = ouvrages + " ("+(i+1)+") "+ emp.getExemplaires().get(i).getTitre() + " ";						
					}					
				}				
				mailExp.setBody("Bonjour " + emp.getUser().getPrenom() + ", Vous n'avez toujours pas rendu votre emprunt contenant : \"" + ouvrages + "\", en date du "+ emp.getDateEcheance() +". Merci de les ramenés dans les plus bref delai. Sous peine de poursuite");
				mailExp.setSendTo(emp.getUser().getEmail());
				restTemplate.postForObject(url, mailExp, EmailTemplate.class);
			}
		});*/
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public void sendEmailWithAttachment(MultipartFile multipartFile, String mail) throws MessagingException, IOException {
//		
//		this.setEmailAddress(mail);
//		MimeMessage msg = javaMailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
//
//		try {
//			if (attchEmailAddr.contains(",")) {
//				String[] emails = attchEmailAddr.split(",");
//				int receipantSize = emails.length;				
//				for (int i = 0; i < receipantSize; i++) {
//					helper.setTo(emails[i]);
//					helper.setSubject(" Votre fichier !");
//					helper.setText("<h1>" + "Chercher fichier à envoyer" + "</h1>", true);
//					InputStreamSource attachment = new ByteArrayResource(multipartFile.getBytes());
//
//					helper.addAttachment(multipartFile.getOriginalFilename(), attachment);
//					javaMailSender.send(msg);
//				}
//
//			} else {
//				helper.setTo(attchEmailAddr);
//				helper.setSubject(" Votre fichier !");
//				// par defaut = text/plain
//				// true = text/html
//				helper.setText("<h1>" + "Vous avez réussi un fichier" + "</h1>", true);
//				InputStreamSource attachment = new ByteArrayResource(multipartFile.getBytes());
//
//				helper.addAttachment(multipartFile.getOriginalFilename(), attachment);
//				javaMailSender.send(msg);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
