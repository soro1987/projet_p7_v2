package fr.soro.dto;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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

    public Date getEarliestBookReturnDate() {
        return earliestBookReturnDate;
    }

    public Long getNumberOfReservation() {
        return numberOfReservation;
    }

    public boolean isCanBeBooked() {
        return canBeBooked;
    }
}
