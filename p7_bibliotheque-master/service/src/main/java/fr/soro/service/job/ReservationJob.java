package fr.soro.service.job;

/*
Send mail at the holder reservation at 2 moment :
    -when booking
    -when returning a book
 */


import fr.soro.entities.Ouvrage;
import fr.soro.entities.Reservation;
import fr.soro.repositories.ReservationRepository;
import fr.soro.service.OuvrageService;
import fr.soro.utilities.UtilitiesComponent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
@Component
public class ReservationJob {

    private final ReservationRepository reservationRepository;
    private final OuvrageService ouvrageService;
    private final UtilitiesComponent utilitiesComponent;

    public ReservationJob( ReservationRepository reservationRepository, OuvrageService ouvrageService, UtilitiesComponent utilitiesComponent) {
        this.reservationRepository = reservationRepository;
        this.ouvrageService = ouvrageService;
        this.utilitiesComponent = utilitiesComponent;
    }




}
