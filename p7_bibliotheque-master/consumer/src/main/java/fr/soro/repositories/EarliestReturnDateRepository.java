package fr.soro.repositories;

import fr.soro.entities.EarliestReturnDate;
import fr.soro.entities.Ouvrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EarliestReturnDateRepository extends JpaRepository <EarliestReturnDate, Long> {
    // fetch an entity by ouvrage
    public EarliestReturnDate findByOuvrage(Ouvrage ouvrage);
}
