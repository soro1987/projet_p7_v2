package fr.soro.batch.modele;


public class MailForExpiredReservationDto {

    private final String userFullName;
    private final String userEmail;
    private final String ouvrageName;

    public MailForExpiredReservationDto(String userFullName, String userEmail, String ouvrageName) {
        this.userFullName = userFullName;
        this.userEmail = userEmail;
        this.ouvrageName = ouvrageName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getOuvrageName() {
        return ouvrageName;
    }
}
