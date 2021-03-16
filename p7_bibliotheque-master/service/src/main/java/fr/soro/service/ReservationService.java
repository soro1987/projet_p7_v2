package fr.soro.service;

import fr.soro.dto.EmailTemplateDTO;
import fr.soro.dto.ReservationAvailabilityDTO;
import fr.soro.entities.Emprunt;
import fr.soro.entities.Ouvrage;
import fr.soro.entities.Reservation;
import fr.soro.entities.User;
import fr.soro.exeption.FunctionalException;
import fr.soro.repositories.EmpruntRepository;
import fr.soro.repositories.OuvrageRepository;
import fr.soro.repositories.ReservationRepository;
import fr.soro.repositories.UserRepository;
import fr.soro.utilities.ReservationTimers;
import fr.soro.utilities.UtilitiesComponent;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.Utilities;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ReservationService {
    @Autowired
    private OuvrageRepository ouvrageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private final ReservationRepository reservationRepository;
    @Autowired
    ReservationTimers timers;
    @Autowired
    UtilitiesComponent utilitiesComponent;
    @Autowired
    EmpruntRepository empruntRepository;
    @Autowired
    ExemplaireService exemplaireService;
    @Autowired
    EarliestReturnDateService earliestReturnDateService;


    public Reservation createReservation(Long userId, Long ouvrageId){
        User user = this.userRepository.getOne(userId);
        Ouvrage ouvrage = this.ouvrageRepository.getOne(ouvrageId);
        Reservation reservation = new Reservation(user,ouvrage);
        return  reservation;
    }

    public Reservation addNew(Reservation reservation) {
        failIfUserAlreadyHaveExemple(reservation);
        failIfBookCountLimitHasReach(reservation);
        failIfUserAlreadyHasBooking(reservation);
        int lowestRank = getHighestRankNumberForExistingReservations(reservation.getOuvrage());
        reservation.setRank(++lowestRank);
        return reservationRepository.save(reservation) ;
    }


    public int getHighestRankNumberForExistingReservations(Ouvrage ouvrage){
        Optional<Reservation> lowestRanked = reservationRepository.findTopByOuvrageIdOrderByRankDesc(ouvrage.getId());
        if(lowestRanked.isPresent()){
            return lowestRanked.get().getRank();
        }
        else{
            return 0;
        }
    }

    private void failIfUserAlreadyHasBooking(Reservation reservation) {
        Optional<Reservation> byUserIdAndOuvrageId = reservationRepository
                .findByUserId(reservation.getUser().getId());
        if (byUserIdAndOuvrageId.isPresent()){
            throw new FunctionalException("Utilisateur a deja réservé cet ouvrage");

        }
    }



    private void failIfBookCountLimitHasReach(Reservation reservation) {
       Long count = reservationRepository
                .countByOuvrageId(reservation.getOuvrage().getId())
                .orElse(0L);
       if (count >= reservation.getOuvrage().getNbreExemplaireDispo()*2){
           throw new FunctionalException("Le nombre maximal de reservation est atteint");

       }


    }

    public void failIfUserAlreadyHaveExemple(Reservation reservation) {
        //Optional<Reservation> exemplaire = empruntRepository
        //        .findByOuvrageIdAndUserId(reservation.getOuvrage().getId(),reservation.getUser().getId());
        boolean found = exemplaireService.doesUserCurrentlyPossessThisBook(reservation.getUser(), reservation.getOuvrage());
        if (found){
            throw new FunctionalException("L'utilisateur a déja l'ouvrage en cours d'emprunt");

        }

    }



    public void cancel(Long reservationId) {
        Optional<Reservation> reservation = Optional.of(this.reservationRepository.getOne(reservationId));
        if (reservation.isPresent()){
            timers.remove(reservation.get());
            this.reservationRepository.deleteById(reservation.get().getId());
            // send email to user telling him his reservation has been cancelled
            EmailTemplateDTO dto = new EmailTemplateDTO(reservation.get().getUser().getEmail(),
                    "Reservation cancelled",
                    "Reservation for " + reservation.get().getOuvrage().getTitre() + "  has been cancelled");
            utilitiesComponent.send_email(dto);
        }else {
            throw new FunctionalException("Error reservation not exist");
        }

    }

    public List<Reservation> listActiveReservatonsMadeByUser(User user){
        // find all reservations by user
        return reservationRepository.findAllByUser(user);
    }

    public ReservationAvailabilityDTO findAvailabilityDetails(long ouvrageID){
        // max reservation number has been reached for this book

        Ouvrage ouvrage = ouvrageRepository.getOne(ouvrageID);
        long count = countReservationNumber(ouvrageID);
        if (count >= ouvrage.getNbreExemplaireDispo()*2){
            return new ReservationAvailabilityDTO(null, -1, true);
        }
        else{
            // get the ealiest date
            Date date = earliestReturnDateService.getEarliestReturnDate(ouvrage);
            return new ReservationAvailabilityDTO(date, (int) count, false);
        }
    }

    private long countReservationNumber(long ouvrageID){
        return reservationRepository
                .countByOuvrageId(ouvrageID)
                .orElse(0L);
    }


}
