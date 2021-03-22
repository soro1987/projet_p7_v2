package fr.soro.service;

import fr.soro.dto.UserReservationsCredentialsDto;
import fr.soro.dto.OuvrageWaitingListCredentialsDto;
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
import java.util.stream.Collectors;

@Service
public class ReservationService {


     private final OuvrageRepository ouvrageRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final UtilitiesComponent utilitiesComponent;
    private final ExemplaireRepository exemplaireRepository;
    private final EmpruntService empruntService;
    private final EmpruntRepository empruntRepository;




    public ReservationService(ReservationJob reservationJob, OuvrageRepository ouvrageRepository, UserRepository userRepository,
                              ReservationRepository reservationRepository, UtilitiesComponent utilitiesComponent, ExemplaireService exemplaireService,
                              ExemplaireRepository exemplaireRepository, EmpruntService empruntService, EmpruntRepository empruntRepository)
    {
        this.ouvrageRepository = ouvrageRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.utilitiesComponent = utilitiesComponent;
        this.exemplaireRepository = exemplaireRepository;
        this.empruntService = empruntService;
        this.empruntRepository = empruntRepository;
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
       Ouvrage ouvrage =ouvrageRepository.findById(reservation.getOuvrage().getId()).orElseThrow(IllegalArgumentException::new);
       if(count >= ouvrage.getNbreExemplaireDispo()*2L){
       //if (count >= reservation.getOuvrage().getNbreExemplaireDispo()*2){
           throw new FunctionalException("Le nombre maximal de reservation est atteint");
       }
    }

    public void failIfUserAlreadyHaveExemple(Reservation reservation) {
        Optional<Reservation> reservation1 = reservationRepository
                .findByOuvrageIdAndUserId(reservation.getOuvrage().getId(),reservation.getUser().getId());
        if (reservation1.isPresent()){
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


    public UserReservationsCredentialsDto findUserReservationCredentials(Long reservationId) {
        //Create object to return
        UserReservationsCredentialsDto userReservationsCredentials = new UserReservationsCredentialsDto();
        Reservation reservation = this.reservationRepository.findById(reservationId).orElseThrow(IllegalArgumentException::new);
            //Retrieve values
            userReservationsCredentials.setBookEarliestReturnDate(
                    this.empruntService.findEmpruntEarliestReturnDate(reservation.getOuvrage().getId()));
            userReservationsCredentials.setTitle(reservation.getOuvrage().getTitre());
            userReservationsCredentials.setPositionInWaitingList(this.positionInWaitingList(reservation));
            return userReservationsCredentials;
    }

    public List<UserReservationsCredentialsDto> findAllUserReservationsCredentials(Long userId){
        return this.reservationRepository.findAllByUserIdOrderByDateReservation(userId)
                        .stream()
                        .map(reservation -> this.findUserReservationCredentials(reservation.getId()))
                        .collect(Collectors.toList());
    }


    private Integer positionInWaitingList(Reservation reservation) {
        List<Reservation> reservations =this.reservationRepository.findAllByOuvrageIdOrderByDateReservationDesc(reservation.getOuvrage().getId());
        return reservations.indexOf(reservation)+1;
    }

    public OuvrageWaitingListCredentialsDto waitingListCredentials(Long ouvrageId) {
        //Create object to return
        OuvrageWaitingListCredentialsDto waitingListCredentialsDto = new OuvrageWaitingListCredentialsDto();
        //Retrieve values
        waitingListCredentialsDto.setEarliestBookReturnDate(this.empruntService.findEmpruntEarliestReturnDate(ouvrageId));
        waitingListCredentialsDto.setNumberOfReservation(reservationRepository.countByOuvrageId(ouvrageId).orElse( 0L));
        waitingListCredentialsDto.setCanBeBooked(this.canBeBooked(ouvrageId));
        return waitingListCredentialsDto;
    }

    public boolean canBeBooked(Long ouvrageId) {
        //Retrieve number of exemplaires and the number of reservation available
        Optional<Ouvrage> ouvrage = ouvrageRepository.findById(ouvrageId);
        Optional<Long> numberOfReservationForTheBook = reservationRepository.countByOuvrageId(ouvrageId);
        //Check if the number of reservation is less then two time the number of exemplaires
        if (ouvrage.isPresent() && numberOfReservationForTheBook.isPresent() ){
            return ouvrage.get().getNbreExemplaireDispo() * 2L < numberOfReservationForTheBook.get();
        }
        return false;
    }

    public void returnEmprunt(Long idEmprunt, Long idExmplaire) {
        Exemplaire exemplaire = this.empruntService.resetExemplaire(idExmplaire);
        Ouvrage ouv = this.empruntService.retrieveAndUpdateOuvrage(exemplaire);
        //Persist the changes in db
        this.exemplaireRepository.save(exemplaire);
        this.empruntRepository.deleteById(idEmprunt);
        //If there is a reservation for this ouvrage send mail to first reservation in line
        this.sendMailToPrioritaryReservationWhenOuvrageIsDisponible(ouv);
    }

}



