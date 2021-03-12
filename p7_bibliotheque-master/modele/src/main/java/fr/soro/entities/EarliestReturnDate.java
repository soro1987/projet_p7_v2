package fr.soro.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity

public class EarliestReturnDate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToOne
    Ouvrage ouvrage;

    Date expectedReturnDate;

    public EarliestReturnDate(Ouvrage ouvrage, Date expectedReturnDate) {
        this.ouvrage = ouvrage;
        this.expectedReturnDate = expectedReturnDate;
    }
}
