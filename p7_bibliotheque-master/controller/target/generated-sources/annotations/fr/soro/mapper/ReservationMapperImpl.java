package fr.soro.mapper;

import fr.soro.dto.ReservationDto;
import fr.soro.entities.Ouvrage;
import fr.soro.entities.Reservation;
import fr.soro.entities.User;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-09T20:34:14+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_282 (Private Build)"
)
@Component
public class ReservationMapperImpl implements ReservationMapper {

    @Override
    public ReservationDto from(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }

        ReservationDto reservationDto = new ReservationDto();

        reservationDto.setOuvrageName( reservationOuvrageTitre( reservation ) );
        reservationDto.setUserName( reservationUserNom( reservation ) );
        reservationDto.setDateReservation( reservation.getDateReservation() );
        reservationDto.setId( reservation.getId() );

        return reservationDto;
    }

    @Override
    public Reservation from(ReservationDto reservationDto) {
        if ( reservationDto == null ) {
            return null;
        }

        User user = null;
        Ouvrage ouvrage = null;

        Reservation reservation = new Reservation( user, ouvrage );

        reservation.setId( reservationDto.getId() );
        reservation.setDateReservation( reservationDto.getDateReservation() );

        return reservation;
    }

    private String reservationOuvrageTitre(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }
        Ouvrage ouvrage = reservation.getOuvrage();
        if ( ouvrage == null ) {
            return null;
        }
        String titre = ouvrage.getTitre();
        if ( titre == null ) {
            return null;
        }
        return titre;
    }

    private String reservationUserNom(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }
        User user = reservation.getUser();
        if ( user == null ) {
            return null;
        }
        String nom = user.getNom();
        if ( nom == null ) {
            return null;
        }
        return nom;
    }
}
