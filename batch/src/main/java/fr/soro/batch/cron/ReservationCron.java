package fr.soro.batch.cron;

import fr.soro.batch.client.ReservationClient;
import fr.soro.batch.modele.EmailTemplate;
import fr.soro.batch.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReservationCron {

    private final ReservationClient reservationClient;
    private final EmailService emailService;

    public ReservationCron(ReservationClient reservationClient, EmailService emailService) {
        this.reservationClient = reservationClient;
        this.emailService = emailService;
    }

    @Scheduled(cron="*/10 * * * * *")
    public void scanforExpireReservation(){
        this.reservationClient.getAllMailForExpiredReservation().forEach(dto -> emailService.sendTextEmail(
                new EmailTemplate(dto.getUserEmail(),"Reservation annulée","Bonjour Madame/Monsieur "+dto.getUserFullName()+", votre réservation concernant " +
                        "l'ouvrage suivant :  "+ dto.getOuvrageName() + " est arrivée à expiration."
        ))) ;

    }
}
