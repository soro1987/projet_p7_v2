package fr.soro.dto;

import java.util.Date;

public class UserReservationCredentialsDto {
    private String title;
    private Date bookEarliestReturnDate;
    private Integer positionInWaitingList;

    public UserReservationCredentialsDto() {
    }

    public UserReservationCredentialsDto(String title, Date bookEarliestReturnDate, Integer positionInWaitingList) {
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