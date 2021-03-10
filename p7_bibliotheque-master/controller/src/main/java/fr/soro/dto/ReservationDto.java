package fr.soro.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationDto {

    public Long id;

    public String userName;

    public String ouvrageName;

    private LocalDateTime dateReservation;


}
