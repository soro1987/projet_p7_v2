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

    private Reservation reservation;

    @Autowired
    UtilitiesComponent utilitiesComponent;


    public ReservationTimerTask(Reservation reservation){
        this.reservation = reservation;
    }

    @Override
    public void run() {
        int copiesLeft = reservation.getOuvrage().getNbreExemplaireDispo();
        Ouvrage ouv = reservation.getOuvrage();
        // if the book is still available,
        // delete the reservation
        reservationRepository.delete(reservation);
        reservationRepository.flush();
        if(copiesLeft > 0){
            // notify the next top ranked person on the Reservation list for the book
            Optional<Reservation> topReserved =
                    reservationRepository.findTopByOuvrageIdOrderByRankAsc(ouv.getId());
            if(topReserved.isPresent()) {
                if(!timers.containsKey(topReserved.get())) {
                    String email = topReserved.get().getUser().getEmail();
                    //Mail sender to alert the No.1 user of availability of the book
                    EmailTemplateDTO dto = new EmailTemplateDTO(email, ouv.getTitre() + " is available", " This book has become available. " +
                            "You have 48 hours to pick it up.");
                    utilitiesComponent.send_email(dto);
                    //Timer to start 48 hour countdown after user has been alerted of availability
                    utilitiesComponent.startTimer(topReserved.get());
                }
            }
        }
        timers.remove(reservation);
        utilitiesComponent.recalculateUpdateReservationRanking(reservation.getOuvrage());
    }

}
