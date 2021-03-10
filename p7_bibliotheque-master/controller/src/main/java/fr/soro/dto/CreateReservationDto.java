package fr.soro.dto;

import lombok.Data;

@Data
public class CreateReservationDto {

    private Long userId;
    private Long ouvrageId;
}
