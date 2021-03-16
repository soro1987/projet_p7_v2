package fr.soro.dto;

import java.util.Date;

public class ReservationAvailabilityDTO {
    Date earliestReturnDate;// used
    int numberPeopleReserveList;
    boolean maximunSlotReach;
    public ReservationAvailabilityDTO() {
    }


    public ReservationAvailabilityDTO(Date earliestReturnDate, int numberPeopleReserveList, boolean maximunSlotReach) {
        this.earliestReturnDate = earliestReturnDate;
        this.numberPeopleReserveList = numberPeopleReserveList;
        this.maximunSlotReach = maximunSlotReach;
    }

    public Date getEarliestReturnDate() {
        return earliestReturnDate;
    }

    public void setEarliestReturnDate(Date earliestReturnDate) {
        this.earliestReturnDate = earliestReturnDate;
    }

    public int getNumberPeopleReserveList() {
        return numberPeopleReserveList;
    }

    public void setNumberPeopleReserveList(int numberPeopleReserveList) {
        this.numberPeopleReserveList = numberPeopleReserveList;
    }

    public boolean isMaximunSlotReach() {
        return maximunSlotReach;
    }

    public void setMaximunSlotReach(boolean maximunSlotReach) {
        this.maximunSlotReach = maximunSlotReach;
    }
}

