package fr.soro.dto;

import java.util.Date;

public class WaitingListCredentialsDto {
    private Long ouvrageId;
    private Date earliestBookReturnDate;
    private Long numberOfReservation;
    private boolean canBeBooked;

    public WaitingListCredentialsDto(Long ouvrageId, Date earliestBookReturnDate, Long numberOfReservation, boolean canBeBooked) {
        this.ouvrageId = ouvrageId;
        this.earliestBookReturnDate = earliestBookReturnDate;
        this.numberOfReservation = numberOfReservation;
        this.canBeBooked = canBeBooked;
    }

    public WaitingListCredentialsDto() {
    }

    public Long getOuvrageId() {
        return ouvrageId;
    }

    public void setOuvrageId(Long ouvrageId) {
        this.ouvrageId = ouvrageId;
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
