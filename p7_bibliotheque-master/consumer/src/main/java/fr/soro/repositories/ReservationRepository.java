package fr.soro.repositories;

import fr.soro.entities.Ouvrage;
import fr.soro.entities.Reservation;
import fr.soro.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByUserId(Long userId);

    Optional<Long> countByOuvrageId(Long ouvrageId);

    Optional<Reservation> findByOuvrageIdAndUserId(Long OuvrageId, Long UserId);

    List<Reservation> findAllByOuvrageId(Long ouvrageId);

    Page<Reservation> findByOuvrageId(Long ouvrageId, Pageable pageable);

    List<Reservation> findAllByUser(User user);

    @Query("select r from #{#entityName} r where r.mailSendTime < ?1")
    List<Reservation> findListReservationMailSentTimePast(LocalDateTime before48h);

    Optional<Reservation> findFirstByOuvrageIdOrderByDateReservationAsc(Long ouvrageId);

    List<Reservation> findAllByOuvrageIdOrderByDateReservationDesc(Long ouvrageId);

    List<Reservation> findAllByUserIdOrderByDateReservation(Long userId);



}
