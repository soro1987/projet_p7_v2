package fr.soro.dto;

public class CreateReservationDto {
    private Long userId;
    private Long ouvrageId;
    private boolean created;


public CreateReservationDto() {
    }

    public CreateReservationDto(Long userId, Long ouvrageId) {
        this.userId = userId;
        this.ouvrageId = ouvrageId;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
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
