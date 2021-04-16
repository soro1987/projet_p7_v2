package fr.soro.dto;

import java.util.Date;

public class UserReservationCredentialsDto {
    private Long id;
    private String title;

    private Date bookEarliestReturnDate;
    private Integer positionInWaitingList;

    public UserReservationCredentialsDto() {
    }

    public UserReservationCredentialsDto(Long id, String title, Date bookEarliestReturnDate, Integer positionInWaitingList) {
        this.id = id;
        this.title = title;
        this.bookEarliestReturnDate = bookEarliestReturnDate;
        this.positionInWaitingList = positionInWaitingList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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