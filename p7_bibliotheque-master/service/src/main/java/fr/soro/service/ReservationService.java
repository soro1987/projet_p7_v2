package fr.soro.service;

import fr.soro.dto.WaitingListCredentialsDto;
import fr.soro.entities.*;
import fr.soro.exeption.FunctionalException;
import fr.soro.repositories.*;
import fr.soro.service.job.ReservationJob;
import fr.soro.utilities.UtilitiesComponent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {




    private final ReservationJob reservationJob;
    private final OuvrageRepository ouvrageRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final UtilitiesComponent utilitiesComponent;
    private final ExemplaireService exemplaireService;
    private final ExemplaireRepository exemplaireRepository;
    private final EmpruntService empruntService;
    private final ReservationService reservationService;



    public ReservationService(ReservationJob reservationJob, OuvrageRepository ouvrageRepository, UserRepository userRepository, ReservationRepository reservationRepository,
                              UtilitiesComponent utilitiesComponent, ExemplaireService exemplaireService, ExemplaireRepository exemplaireRepository, EmpruntService empruntService, ReservationService reservationService)
    {
        this.reservationJob = reservationJob;
        this.ouvrageRepository = ouvrageRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.utilitiesComponent = utilitiesComponent;
        this.exemplaireService = exemplaireService;
        this.exemplaireRepository = exemplaireRepository;
        this.empruntService = empruntService;
        this.reservationService = reservationService;
    }

    public Reservation createReservation(Long userId, Long ouvrageId){
        User user = this.userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        Ouvrage ouvrage = this.ouvrageRepository.findById(ouvrageId).orElseThrow(IllegalArgumentException::new);
        return new Reservation(user,ouvrage);
    }

    public Reservation addNew(Reservation reservation) {
        failIfUserAlreadyHaveExemple(reservation);
        failIfBookCountLimitHasReach(reservation);
        failIfUserAlreadyHasBooking(reservation);
        Reservation saved = reservationRepository.save(reservation) ;
        utilitiesComponent.recalculateUpdateReservationRanking(saved.getOuvrage());
        this.sendMailToPrioritaryReservationWhenOuvrageIsDisponible(saved.getOuvrage());
        return saved;
    }

    public void sendMailToPrioritaryReservationWhenOuvrageIsDisponible(Ouvrage ouvrage) {
        //If ouvrage is available check if there is a reservation for this ouvrage
        if (ouvrage.getNbreExemplaireDispo() > 0) {
            Optional<Reservation> firstReservation = this.reservationRepository.findFirstByOuvrageIdOrderByDateReservationAsc(ouvrage.getId());
            //if reservation is present  send mail to warn the user
            if (firstReservation.isPresent()) {
                this.utilitiesComponent.sendMailBuilder(
                        firstReservation.get().getUser().getEmail(),
                        ouvrage.getTitre(),
                        "is available, You have 48 hours to pick it up.");
                //Persist the attribut mailSendTime of reservation in db
                firstReservation.get().setMailSendTime(LocalDateTime.now());
                this.reservationRepository.save(firstReservation.get());
            }
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
       Ouvrage ouvrage =ouvrageRepository.findById(reservation.getOuvrage().getId()).get();
       if(count >= ouvrage.getNbreExemplaireDispo()*2){
       //if (count >= reservation.getOuvrage().getNbreExemplaireDispo()*2){
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
        Optional<Reservation> reservation = this.reservationRepository.findById(reservationId);
        if (reservation.isPresent()){
            this.reservationRepository.deleteById(reservation.get().getId());
        }else {
            throw new FunctionalException("Error reservation not exist");
        }
    }

    public List<Reservation> expireReservations() {
        List<Reservation> reservations = reservationRepository.findListReservationMailSentTimePast(
                LocalDateTime.now().minus(48, ChronoUnit.HOURS));
        reservations.forEach(reservation -> {
            this.cancel(reservation.getId());
            this.sendMailToPrioritaryReservationWhenOuvrageIsDisponible(reservation.getOuvrage());
        });
        return reservations;
    }

    public Optional<Long> numberOfReservationForTheBook(Long ouvrageId) {
        return reservationRepository.countByOuvrageId(ouvrageId);
    }

    public UserReservationsCredentialsDto findUserReservationsCredentials(Long reservationId) {
        //Create object to return
        UserReservationsCredentialsDto userReservationsCredentialsDto = new UserReservationsCredentialsDto();
        Optional<Reservation> reservation = this.reservationRepository.findById(reservationId);
            //Retrieve values
            userReservationsCredentialsDto.setBookEarliestReturnDate( this.empruntService.findEmpruntEarliestReturnDate(reservation.get().getOuvrage().getId()));
            userReservationsCredentialsDto.setTitle(reservation.get().getOuvrage().getTitre());
            userReservationsCredentialsDto.setPositionInWaitingList(this.positionInWaitingList(reservation.get()));
            return userReservationsCredentialsDto;
    }

    private Integer positionInWaitingList(Reservation reservation) {
        List<Reservation> reservations =this.reservationRepository.findAllByOuvrageIdOrderByDateReservationDesc(reservation.getOuvrage().getId());
        return reservations.indexOf(reservation)+1;
    }

    public WaitingListCredentialsDto waitingListCredentials(Long ouvrageId) {
        //Create object to return
        WaitingListCredentialsDto waitingListCredentialsDto = new WaitingListCredentialsDto();
        //Retrieve values
        waitingListCredentialsDto.setEarliestBookReturnDate(this.empruntService.findEmpruntEarliestReturnDate(ouvrageId));
        waitingListCredentialsDto.setNumberOfReservation(this.reservationService.numberOfReservationForTheBook(ouvrageId).get());
        waitingListCredentialsDto.setCanBeBooked(this.empruntService.canBeBooked(ouvrageId));
        return waitingListCredentialsDto;
    }

}



