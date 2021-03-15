package fr.soro.dto;

import lombok.Data;

public class CreateReservationDto {

    private Long userId;
    private Long ouvrageId;

    public CreateReservationDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOuvrageId() {
        return ouvrageId;
    }

    public void setOuvrageId(Long ouvrageId) {
        this.ouvrageId = ouvrageId;
    }
}
