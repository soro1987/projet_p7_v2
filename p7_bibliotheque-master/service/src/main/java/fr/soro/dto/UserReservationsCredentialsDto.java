package fr.soro.dto;

import java.util.Date;

public class UserReservationsCredentialsDto {
    private String title;
    private Date bookEarliestReturnDate;
    private Integer positionInWaitingList;

    public UserReservationsCredentialsDto() {
    }

    public UserReservationsCredentialsDto(String title, Date bookEarliestReturnDate, Integer positionInWaitingList) {
        this.title = title;
        this.bookEarliestReturnDate = bookEarliestReturnDate;
        this.positionInWaitingList = positionInWaitingList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getBookEarliestReturnDate() {
        return bookEarliestReturnDate;
    }

    public void setBookEarliestReturnDate(Date bookEarliestReturnDate) {
        this.bookEarliestReturnDate = bookEarliestReturnDate;
    }

    public Integer getPositionInWaitingList() {
        return positionInWaitingList;
    }

    public void setPositionInWaitingList(Integer positionInWaitingList) {
        this.positionInWaitingList = positionInWaitingList;
    }
}