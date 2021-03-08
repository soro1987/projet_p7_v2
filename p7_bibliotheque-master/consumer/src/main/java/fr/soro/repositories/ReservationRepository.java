package fr.soro.repositories;

import fr.soro.entities.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findByUserIdAndOuvrageId(Long userId, Long ouvrageId);

    Optional<Long> countByOuvrageId(Long ouvrageId);

    Optional<Reservation> findByOuvrageIdAndUserId(Long OuvrageId, Long UserId);

    Page<Reservation> findByOuvrageId(Long ouvrageId, Pageable pageable);

    Optional<Reservation> findTopByOuvrageIdOrderByRankDesc(Long ouvrageId);


}
