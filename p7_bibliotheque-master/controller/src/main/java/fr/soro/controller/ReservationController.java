package fr.soro.controller;

import fr.soro.dto.*;
import fr.soro.entities.Reservation;
import fr.soro.mapper.ReservationMapper;
import fr.soro.repositories.EmpruntRepository;
import fr.soro.repositories.ReservationRepository;
import fr.soro.service.EmpruntService;
import fr.soro.service.ReservationService;
import fr.soro.service.UserReservationsCredentialsDto;
import fr.soro.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ReservationController {


    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;
    private final ReservationRepository reservationRepository;
    private final MailForExpiredReservationDto mailForExpiredReservationDto;
    private final UserService userService;
    private final EmpruntService empruntService;
    private final WaitingListCredentialsDto waitingListCredentialsDto;
    private final EmpruntRepository empruntRepository;
    private UserReservationsCredentialsDto userReservationsCredentialsDto;

    public ReservationController(ReservationService reservationService, ReservationMapper reservationMapper, ReservationRepository reservationRepository,
                                 MailForExpiredReservationDto mailForExpiredReservationDto, UserService userService, EmpruntService empruntService,
                                 WaitingListCredentialsDto waitingListCredentialsDto, EmpruntRepository empruntRepository ,UserReservationsCredentialsDto userReservationsCredentialsDto) {
        this.reservationService = reservationService;
        this.reservationMapper = reservationMapper;
        this.reservationRepository = reservationRepository;
        this.mailForExpiredReservationDto = mailForExpiredReservationDto;
        this.userService = userService;
        this.empruntService = empruntService;
        this.waitingListCredentialsDto = waitingListCredentialsDto;
        this.empruntRepository = empruntRepository;

        this.userReservationsCredentialsDto = userReservationsCredentialsDto;
    }

    @GetMapping(value = "/v1/ouvrages/{id}/reservations")
    public ResponseEntity<Page<ReservationDto>> getReservation(@PathVariable Long id, Pageable pageable) {
        Page<Reservation> reservationPage = reservationRepository.findByOuvrageId(id,pageable);
        return ResponseEntity.ok(reservationPage.map(reservationMapper::from));
    }


    @PostMapping(value = "/reservations")
    public ResponseEntity<ReservationDto> createReservation(@RequestBody CreateReservationDto createReservationDto) {
        Reservation createReservation = reservationService.createReservation(createReservationDto.getUserId(), createReservationDto.getOuvrageId());
        Reservation newReservationAdded = reservationService.addNew(createReservation);
        return new ResponseEntity<>(reservationMapper.from(newReservationAdded), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable(value = "reservationId") Long reservationId) {
        reservationService.cancel(reservationId);
        return  ResponseEntity.ok().build();
    }

    //Recuperer toutes les reservation de + de 48h
    //Les supprimer de la db
    @GetMapping (value = "/reservations/expired")
    public ResponseEntity <List<MailForExpiredReservationDto>> expireReservations(){
           return  ResponseEntity.ok (this.reservationService.expireReservations()
                   .stream().map(reservation -> new MailForExpiredReservationDto(reservation.getUser().getFullName(),
                           reservation.getUser().getEmail(),reservation.getOuvrage().getTitre()))
                   .collect(Collectors.toList()));

    }

    @GetMapping(value = "/v1/reservations/waitingList/{ouvrageId}")
    public ResponseEntity<WaitingListCredentialsDto> checkOuvrageWaitingList(@PathVariable Long ouvrageId) {
        Date empruntEarliestReturnDate = empruntService.findEmpruntEarliestReturnDate(ouvrageId);
        Optional<Long> numberOfReservationForTheBook = reservationService.numberOfReservationForTheBook(ouvrageId);
        boolean canBeBooked = empruntService.canBeBooked(ouvrageId);
        WaitingListCredentialsDto waitingListCredentialsDto = new WaitingListCredentialsDto(empruntEarliestReturnDate,
                numberOfReservationForTheBook.get(),canBeBooked);
        return ResponseEntity.ok(waitingListCredentialsDto);
    }

    @GetMapping(value = "/v1/reservation/userCredentials/{userId}")
    public ResponseEntity<UserReservationsCredentialsDto> userReservationsCredentials(@PathVariable Long reservationId){
        UserReservationsCredentialsDto userReservationsCredentialsDto = this.reservationService.findUserReservationsCredentials(reservationId);
          return ResponseEntity.ok(userReservationsCredentialsDto);
    }















}
