package fr.soro.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "reservation")
public class Reservation  {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private LocalDateTime dateReservation = LocalDateTime.now();

    @Column(name = "mail_send_time")
    public LocalDateTime mailSendTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="ouvrage_id")
    private Ouvrage ouvrage;

    public Reservation() {
    }

    public Reservation(User user, Ouvrage ouvrage) {
        this.user = user;
        this.ouvrage = ouvrage;
    }

    public Reservation(Long id, User user, LocalDateTime dateReservation, LocalDateTime mailSendTime, Ouvrage ouvrage) {
        this.id = id;
        this.user = user;
        this.dateReservation = dateReservation;
        this.mailSendTime = mailSendTime;
        this.ouvrage = ouvrage;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public LocalDateTime getMailSendTime() {
        return mailSendTime;
    }

    public void setMailSendTime(LocalDateTime mailSendTime) {
        this.mailSendTime = mailSendTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDateTime dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Ouvrage getOuvrage() {
        return ouvrage;
    }

    public void setOuvrage(Ouvrage ouvrage) {
        this.ouvrage = ouvrage;
    }

}
