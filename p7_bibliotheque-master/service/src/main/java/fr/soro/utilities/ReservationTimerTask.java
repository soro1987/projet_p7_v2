package fr.soro.utilities;

import fr.soro.dto.EmailTemplateDTO;
import fr.soro.entities.Ouvrage;
import fr.soro.entities.Reservation;
import fr.soro.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class ReservationTimerTask extends TimerTask {
    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ReservationTimers timers;

    @Autowired
    private RestTemplate restTemplate;

    private Reservation reservation;

    private final String EMAIL_SENDER_SERVICE = "http://localhost:8083/sendEmail";

    public ReservationTimerTask(Reservation reservation){
        this.reservation = reservation;
    }

    @Override
    public void run() {
        int copiesLeft = reservation.getOuvrage().getNbreExemplaireDispo();
        Ouvrage ouv = reservation.getOuvrage();
        // if the book is still available,
        if(copiesLeft > 0){
            Optional<Reservation> topReserved =
                    reservationRepository.findTopByOuvrageIdOrderByRankAsc(ouv.getId());
            if(topReserved.isPresent()) {
                String email = topReserved.get().getUser().getEmail();
                //Mail sender to alert the No.1 user of availability of the book
                EmailTemplateDTO dto = new EmailTemplateDTO(email, ouv.getTitre() + " is available", " This book has become available. " +
                        "You have 48 hours to pick it up.");
                send_email(dto);
                //Timer to start 48 hour countdown after user has been alerted of availability
                startTimer(topReserved.get());
            }
        }
        timers.remove(reservation);
        // delete the reservation
        reservationRepository.delete(reservation);
        // notify the next top ranked person on the Reservation list for the book

    }

    @Async
    private void send_email(EmailTemplateDTO dto){
        ResponseEntity<String> result = restTemplate.postForEntity(EMAIL_SENDER_SERVICE, dto, String.class);
    }

    @Async
    private void startTimer(Reservation reservation){
        Timer timer = new Timer();
        timer.schedule(new ReservationTimerTask(reservation), 172800000 ); // delay period
        timers.put(reservation, timer);
    }
}
