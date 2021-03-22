package fr.soro.utilities;

import fr.soro.dto.EmailTemplateDTO;
import fr.soro.entities.Ouvrage;
import fr.soro.entities.Reservation;
import fr.soro.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ReservationRepository reservationRepository;


    @Autowired
    private RestTemplate restTemplate;

    private final String EMAIL_SENDER_SERVICE = "http://localhost:8083/sendEmail";


    public void sendMailBuilder(String userEmail, String ouvrageTitle,String message) {
        //Mail sender to alert the No.1 user of availability of the book
        EmailTemplateDTO emailTemplateDTO = new EmailTemplateDTO(userEmail, ouvrageTitle + message);
        this.sendEmail(emailTemplateDTO);
    }

    @Async
    public void sendEmail(EmailTemplateDTO emailTemplateDTO){
        ResponseEntity<String> result = restTemplate.postForEntity(EMAIL_SENDER_SERVICE, emailTemplateDTO, String.class);
    }


}
