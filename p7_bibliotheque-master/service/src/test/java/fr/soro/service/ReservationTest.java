package fr.soro.service;

import fr.soro.dto.OuvrageWaitingListInfosDto;
import fr.soro.dto.UserReservationsInfosDto;
import fr.soro.entities.Ouvrage;
import fr.soro.entities.Reservation;
import fr.soro.exeption.FunctionalException;
import fr.soro.repositories.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

public class ReservationTest {


    private EmpruntRepository empruntRepository;
    private UserRepository userRepository ;
    private ExemplaireRepository exemplaireRepository ;
    private OuvrageRepository ouvrageRepository ;
    private ReservationRepository reservationRepository ;
    private ReservationService reservationService ;
    private  ExemplaireService exemplaireService;
    private EmpruntService empruntService;



    @BeforeEach
    void init() {
        empruntRepository = Mockito.mock(EmpruntRepository.class);
        empruntService = Mockito.mock(EmpruntService.class);
        exemplaireService = Mockito.mock(ExemplaireService.class);
        userRepository = Mockito.mock(UserRepository.class);
        exemplaireRepository = Mockito.mock(ExemplaireRepository.class);
        ouvrageRepository = Mockito.mock(OuvrageRepository.class);
        reservationRepository = Mockito.mock(ReservationRepository.class);
        reservationService = new ReservationService(ouvrageRepository,userRepository,reservationRepository, null,exemplaireService, exemplaireRepository,empruntService,empruntRepository);

    }


    @Test
    public void shouldTrowFunctionalExeptionWhenOptionalEmpty(){
        Optional<Reservation> reservation = Optional.empty();
        Mockito.doReturn(Optional.empty()).when(reservationRepository).findById(10L);
        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(FunctionalException.class, () -> {
            reservationService.cancel(10L);
        });
        String expectedMessage = "Error reservation not exist";
        String actualMessage = exception.getMessage();
        org.junit.jupiter.api.Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldSetUserReservationsCredentialsDto(){
        List<Reservation> reservations = new ArrayList<>();
        Ouvrage ouvrage = new Ouvrage();
        ouvrage.setId(1L);
        Reservation reservation = new Reservation();
        reservation.setOuvrage(ouvrage);
        reservation.setId(1L);
        reservations.add(reservation);
        Mockito.doReturn(Optional.of(reservation)).when(reservationRepository).findById(1L);
        Mockito.doReturn(new Date()).when(empruntService).findEmpruntEarliestReturnDate(1L);
        Mockito.doReturn(reservations).when(reservationRepository).findAllByOuvrageIdOrderByDateReservationDesc(1L);

        UserReservationsInfosDto userReservationsCredentialsDto = reservationService.findUserReservationCredentials(1L);

        Assertions.assertThat(userReservationsCredentialsDto.getPositionInWaitingList()).isEqualTo(1);

    }
    @Test
    public void shouldSetListUserReservationsCredentialsDto(){
        //previous test
        List<Reservation> reservations = new ArrayList<>();
        Ouvrage ouvrage = new Ouvrage();
        ouvrage.setId(1L);
        Reservation reservation = new Reservation();

        //previous test
        reservation.setOuvrage(ouvrage);
        reservation.setId(1L);
        reservations.add(reservation);
        Mockito.doReturn(new Date()).when(empruntService).findEmpruntEarliestReturnDate(1L);
        Mockito.doReturn(reservations).when(reservationRepository).findAllByOuvrageIdOrderByDateReservationDesc(1L);

        Reservation reservation2 = new Reservation();
        reservation2.setId(1L);
        Ouvrage ouvrage2 = new Ouvrage();
        ouvrage2.setId(1L);
        reservation2.setOuvrage(ouvrage2);
        Reservation reservation3 = new Reservation();
        reservation3.setId(2L);
        Ouvrage ouvrage3 = new Ouvrage();
        ouvrage3.setId(3L);
        reservation3.setOuvrage(ouvrage3);

        List<Reservation> reservationsUser = new ArrayList<>();
        reservationsUser.add(reservation2);
        reservationsUser.add(reservation3);
        Mockito.doReturn(reservationsUser).when(reservationRepository).findAllByUserIdOrderByDateReservation(1L);
        Mockito.doReturn(Optional.of(reservation2)).when(reservationRepository).findById(1L);
        Mockito.doReturn(Optional.of(reservation3)).when(reservationRepository).findById(2L);

        List<UserReservationsInfosDto> userReservationsCredentialsDtos = reservationService
                .findAllUserReservationsCredentials(1L);

        Assertions.assertThat(userReservationsCredentialsDtos.get(0).getId()).isEqualTo(1);
        Assertions.assertThat(userReservationsCredentialsDtos.get(1).getId()).isEqualTo(2);

    }

    @Test
    public void shouldSetUserwaitingListCredentials(){
        Date date = new Date(2021, Calendar.MARCH,2);
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        Ouvrage ouvrage = new Ouvrage();
        ouvrage.setId(1L);
        Mockito.doReturn(Optional.of(4L))
                .doReturn(Optional.of(4L))
                .when(reservationRepository).countByOuvrageId(1L);
        Mockito.doReturn(date).when(empruntService).findEmpruntEarliestReturnDate(1L);
        Mockito.doReturn(Optional.of(ouvrage)).when(ouvrageRepository).findById(1L);

        OuvrageWaitingListInfosDto ouvrageWaitingListInfosDto1 = reservationService.waitingListCredentials(1L);

        Assertions.assertThat(ouvrageWaitingListInfosDto1.getEarliestBookReturnDate()).isEqualTo(date);
        Assertions.assertThat(ouvrageWaitingListInfosDto1.getNumberOfReservation()).isEqualTo(4);
        Assertions.assertThat(ouvrageWaitingListInfosDto1.isCanBeBooked()).isFalse();
        Assertions.assertThat(ouvrageWaitingListInfosDto1.getOuvrageId()).isEqualTo(1L);

    }

}
