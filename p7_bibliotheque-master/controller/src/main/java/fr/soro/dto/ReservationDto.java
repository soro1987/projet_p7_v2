package fr.soro.dto; 

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;


public class ReservationDto {

    private Long id;

    private String userName;

    private String ouvrageName;

    private LocalDateTime dateReservation;

    public ReservationDto() {
    }

    public ReservationDto(Long id, String userName, String ouvrageName, LocalDateTime dateReservation) {
        this.id = id;
        this.userName = userName;
        this.ouvrageName = ouvrageName;
        this.dateReservation = dateReservation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOuvrageName() {
        return ouvrageName;
    }

    public void setOuvrageName(String ouvrageName) {
        this.ouvrageName = ouvrageName;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }
}
