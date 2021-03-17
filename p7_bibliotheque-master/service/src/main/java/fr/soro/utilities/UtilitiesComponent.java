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

    @Async
    public void recalculateUpdateReservationRanking(Ouvrage ouvrage){

        //Fetch every one in the waitting list, put them in a list
        List<Reservation> waitingList =reservationRepository.findAllByOuvrageId(ouvrage.getId());
        //Sort the list by their current rank
        Collections.sort(waitingList);
        //Update their rank to their  current position in the arraylist
        for (int i = 0; i < waitingList.size(); i++) {
           Reservation r = waitingList.get(i);
           r.setRank(i + 1);
           reservationRepository.save(r);
        }
        reservationRepository.flush();
    }
}
