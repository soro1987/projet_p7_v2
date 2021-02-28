package fr.soro.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.soro.entities.Emprunt;
@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {

	List<Emprunt> findByDateDebut(Date datedebut);

	List<Emprunt> findByDateEcheance(Date dateEcheance);

	List<Emprunt> findByProlongation(boolean prolongation);

	List<Emprunt> findByDepassement(int depassement);

	

}
