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

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private LocalDateTime dateReservation;

    @ManyToOne
    @JoinColumn(name="ouvrage_id")
    private Ouvrage ouvrage;


    private Integer rank;

    public Reservation(User user, Ouvrage ouvrage) {
        this.user = user;
        this.ouvrage = ouvrage;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(dateReservation, that.dateReservation) && Objects.equals(ouvrage, that.ouvrage) && Objects.equals(rank, that.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, dateReservation, ouvrage, rank);
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
