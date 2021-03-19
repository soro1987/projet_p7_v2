package fr.soro.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "reservation")
public class Reservation implements Serializable, Comparable<Reservation> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private Integer rank;

    private LocalDateTime dateReservation = LocalDateTime.now();

    public LocalDateTime mailSendTime;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="ouvrage_id")
    private Ouvrage ouvrage;

    public Reservation() {
    }

    public Reservation(User user, Ouvrage ouvrage) {
        this.user = user;
        this.ouvrage = ouvrage;
    }

    public Reservation(Long id, User user, LocalDateTime dateReservation, LocalDateTime mailSendTime, Ouvrage ouvrage, Integer rank) {
        this.id = id;
        this.user = user;
        this.dateReservation = dateReservation;
        this.mailSendTime = mailSendTime;
        this.ouvrage = ouvrage;
        this.rank = rank;
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

    public void setRank(Integer rank) {
        this.rank = rank;
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

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Reservation reservation) {
        if(this.getRank() < reservation.getRank()){
            return 1;
        }
        else if(this.getRank() > reservation.getRank()){
            return -1;
        }
        return 0;
    }
/*
    public static void main(String[] args) {
        Reservation r1 = new Reservation();
        Reservation r5 = new Reservation();
        List<Reservation> list = new ArrayList<>();
        list.add(r1);
        list.add(r5);
        Collections.sort(list);
        // loop through arraylist
    }

 */
}
