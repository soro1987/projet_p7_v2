package fr.soro.batch.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import fr.soro.batch.client.EmpruntClient;
import fr.soro.batch.modele.EmailTemplate;
import fr.soro.batch.modele.EmpruntDto;
import fr.soro.batch.modele.UserDto;

@Service
public class EmailService {

	private static final Logger LOG = LoggerFactory.getLogger(EmailService.class.getName());
	private final JavaMailSender javaMailSender;
	private final EmpruntClient empruntClient ;

	public EmailService(JavaMailSender javaMailSender, EmpruntClient empruntClient) {
		this.javaMailSender = javaMailSender;
		this.empruntClient = empruntClient;
	}

	/*
	 * Methode pour generer les mails
	 */
	@Scheduled(cron="*/10 * * * * *")//	Pour test toutes les 10 secondes
	public void emailInitializer() {
		EmailTemplate mailExp = new EmailTemplate();	
		List<UserDto> usersMailsExpired = empruntClient.getUserExpireEmprunts();
		List<EmpruntDto> empruntExpired = empruntClient.getExpireEmprunts();

		if (empruntExpired.size() > 0){
			for(UserDto user : usersMailsExpired)
			{
				for(EmpruntDto emprunt : empruntExpired)
				{
					LOG.info("=================================Email-Send-Console-Log===================================");
					mailExp.setSubject("Expiration de l'emprunt");
					StringBuilder builder = new StringBuilder();
					builder.append("Bonjour ").append(user.getNom())
							.append("vous deviez rendre votre emprunt numeroter : ")
							.append(emprunt.getId())
							.append("en date du ")
							.append(emprunt.getDateEcheance())
							.append(emprunt.isProlongation()?
									". Merci de les ramenés dans les plus bref delai. Sous peine de poursuite !"
									: ". Vous avez encore la possibiliter de le prolonger en vous connectant dans votre espace personnel sur http://localhost:8080/login. Cordialement!");
					mailExp.setBody(builder.toString());
					mailExp.setSendTo(user.getEmail());
					this.sendTextEmail(mailExp);
				}
			}
		}
	}	

	public void sendTextEmail(EmailTemplate emailTemplate) {
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(emailTemplate.getSendTo().split(","));
			msg.setSubject(emailTemplate.getSubject());
			msg.setText(emailTemplate.getBody());
			javaMailSender.send(msg);
		}
		catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
}