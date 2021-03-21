package fr.soro.dto;

import java.util.Date;

public class WaitingListCredentialsDto {
    private Date earliestBookReturnDate;
    private Long numberOfReservation;
    private boolean canBeBooked;

    public WaitingListCredentialsDto(Date earliestBookReturnDate, Long numberOfReservation, boolean canBeBooked) {
        this.earliestBookReturnDate = earliestBookReturnDate;
        this.numberOfReservation = numberOfReservation;
        this.canBeBooked = canBeBooked;
    }

    public WaitingListCredentialsDto() {

    }

    public Date getEarliestBookReturnDate() {
        return earliestBookReturnDate;
    }

    public Long getNumberOfReservation() {
        return numberOfReservation;
    }

    public boolean isCanBeBooked() {
        return canBeBooked;
    }

    public void setEarliestBookReturnDate(Date earliestBookReturnDate) {
        this.earliestBookReturnDate = earliestBookReturnDate;
    }

    public void setNumberOfReservation(Long numberOfReservation) {
        this.numberOfReservation = numberOfReservation;
    }

    public void setCanBeBooked(boolean canBeBooked) {
        this.canBeBooked = canBeBooked;
    }
}
