package fr.soro.controller;


import fr.soro.dto.*;
import fr.soro.entities.Reservation;
import fr.soro.mapper.ReservationMapper;
import fr.soro.repositories.EmpruntRepository;
import fr.soro.repositories.ReservationRepository;
import fr.soro.service.EmpruntService;
import fr.soro.service.ReservationService;
import fr.soro.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReservationController {

    private  MailForExpiredReservationDto mailForExpiredReservationDto;
    private  OuvrageWaitingListCredentialsDto waitingListCredentialsDto;

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;
    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final EmpruntService empruntService;


    private final EmpruntRepository empruntRepository;
    private UserReservationsCredentialsDto userReservationsCredentialsDto;

    public ReservationController(ReservationService reservationService, ReservationMapper reservationMapper,
                                 ReservationRepository reservationRepository,UserService userService,
                                 EmpruntService empruntService,EmpruntRepository empruntRepository) {
        this.reservationService = reservationService;
        this.reservationMapper = reservationMapper;
        this.reservationRepository = reservationRepository;
        this.userService = userService;
        this.empruntService = empruntService;
        this.empruntRepository = empruntRepository;
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
    @PostMapping (value = "/reservations/expired")
    public ResponseEntity <List<MailForExpiredReservationDto>> expireReservations(){
           return  ResponseEntity.ok (this.reservationService.expireReservations()
                   .stream().map(reservation -> new MailForExpiredReservationDto(reservation.getUser().getFullName(),
                           reservation.getUser().getEmail(),reservation.getOuvrage().getTitre()))
                   .collect(Collectors.toList()));
    }

    @GetMapping(value = "/v1/reservations/waitingList/{ouvrageId}")
    public ResponseEntity<OuvrageWaitingListCredentialsDto> checkOuvrageWaitingList(@PathVariable Long ouvrageId) {
        return ResponseEntity.ok(this.reservationService.waitingListCredentials(ouvrageId));
    }

    @GetMapping(value = "/v1/reservation/userCredentials/{userId}")
    public ResponseEntity<List<UserReservationsCredentialsDto>> userAllReservationsCredentials(@PathVariable Long userId){
        return ResponseEntity.ok(this.reservationService.findAllUserReservationsCredentials(userId));
    }

    @GetMapping(value = "/v1/reservation/userCredentials/{reservationId}")
    public ResponseEntity<UserReservationsCredentialsDto> userReservationsCredentials(@PathVariable Long reservationId){
         return ResponseEntity.ok(this.reservationService.findUserReservationCredentials(reservationId));
    }















}
