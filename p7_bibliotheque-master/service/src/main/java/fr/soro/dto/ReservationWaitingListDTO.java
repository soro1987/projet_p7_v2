package fr.soro.dto;

import fr.soro.entities.Reservation;

import java.util.Date;

public class ReservationWaitingListDTO {
    public Reservation reservation;
    public Date earliestReturnDate;


    public ReservationWaitingListDTO() {
    }

    public ReservationWaitingListDTO(Reservation reservation, Date earliestReturnDate) {
        this.reservation = reservation;
        this.earliestReturnDate = earliestReturnDate;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Date getEarliestReturnDate() {
        return earliestReturnDate;
    }

    public void setEarliestReturnDate(Date earliestReturnDate) {
        this.earliestReturnDate = earliestReturnDate;
    }
}
