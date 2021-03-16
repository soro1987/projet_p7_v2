package fr.soro.controller;

import fr.soro.dto.CreateReservationDto;
import fr.soro.dto.ReservationAvailabilityDTO;
import fr.soro.dto.ReservationDto;
import fr.soro.entities.Ouvrage;
import fr.soro.entities.Reservation;
import fr.soro.mapper.ReservationMapper;
import fr.soro.repositories.ReservationRepository;
import fr.soro.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class ReservationController {


    private final ReservationService reservationService;

    private final ReservationMapper reservationMapper;

    private final ReservationRepository reservationRepository;

    @GetMapping(value = "/v1/ouvrages/{id}/reservations")
    public ResponseEntity<Page<ReservationDto>> getReservation(@PathVariable Long id, Pageable pageable) {
        Page<Reservation> reservationPage = reservationRepository.findByOuvrageId(id,pageable);
        return ResponseEntity.ok(reservationPage.map(reservationMapper::from));
    }


    @PostMapping(value = "/v1/reservations")
    public ResponseEntity<ReservationDto> createReservation(@RequestBody CreateReservationDto createReservationDto) {
        Reservation createReservation = reservationService.createReservation(createReservationDto.getUserId(), createReservationDto.getOuvrageId());
        Reservation newReservationAdded = reservationService.addNew(createReservation);
        return new ResponseEntity<>(reservationMapper.from(newReservationAdded), HttpStatus.CREATED);
    }




    @DeleteMapping(value = "/reservation/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable(value = "reservationId") Long reservationId) {
        reservationService.cancel(reservationId);
        return new ResponseEntity<Void>(HttpStatus.GONE);
    }

    @GetMapping(value = "/v1/reservations/{ouvrageId}")
    public ResponseEntity<ReservationAvailabilityDTO> getReservationAvailability(@PathVariable(value = "ouvrageId") Long ouvrageId) {
        ReservationAvailabilityDTO availabilityDetails = reservationService.findAvailabilityDetails(ouvrageId);
        return ResponseEntity.ok(availabilityDetails);
    }








}
