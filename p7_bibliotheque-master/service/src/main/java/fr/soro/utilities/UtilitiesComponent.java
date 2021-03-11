package fr.soro.utilities;

import fr.soro.dto.EmailTemplateDTO;
import fr.soro.entities.Reservation;
import fr.soro.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Timer;

@Component
public class UtilitiesComponent {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ReservationTimers timers;

    @Autowired
    private RestTemplate restTemplate;

    private final String EMAIL_SENDER_SERVICE = "http://localhost:8083/sendEmail";

    @Async
    public void send_email(EmailTemplateDTO dto){
        ResponseEntity<String> result = restTemplate.postForEntity(EMAIL_SENDER_SERVICE, dto, String.class);
    }

    @Async
    public void startTimer(Reservation reservation){
        Timer timer = new Timer();
        timer.schedule(new ReservationTimerTask(reservation), 172800000 ); // delay period
        timers.put(reservation, timer);
    }
}
