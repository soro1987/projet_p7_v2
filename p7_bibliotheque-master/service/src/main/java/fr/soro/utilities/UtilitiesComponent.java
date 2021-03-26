package fr.soro.utilities;

import fr.soro.dto.EmailTemplateDTO;
import fr.soro.entities.Ouvrage;
import fr.soro.entities.Reservation;
import fr.soro.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Timer;

@Component
public class UtilitiesComponent {

    private final RestTemplate restTemplate;
    @Value("${app.batch.email.service.url}")
    private String emailServiceUrl;

    public UtilitiesComponent(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendOuvrageAvailabilityMail(String userEmail, String fullName, String ouvrageTitle) {
        //Mail sender to alert the No.1 user of availability of the book
        EmailTemplateDTO emailTemplateDTO = new EmailTemplateDTO(userEmail, "Ouvrage disponible",
                "Bonjour Monsieur/Madame " + fullName + " votre reservation concernat l'ouvrage " + ouvrageTitle +
                " est disponoble. vous disposez de 48h pour passer le retirer.");
        this.sendEmail(emailTemplateDTO);
    }

    @Async
    public void sendEmail(EmailTemplateDTO emailTemplateDTO){
        restTemplate.postForEntity(emailServiceUrl, new HttpEntity<>(emailTemplateDTO), String.class);
    }

}
