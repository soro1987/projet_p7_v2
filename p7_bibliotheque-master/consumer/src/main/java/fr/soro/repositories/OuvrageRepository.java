package fr.soro.repositories;

import fr.soro.entities.Ouvrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OuvrageRepository extends JpaRepository<Ouvrage, Long> {

	@Query("Select o from #{#entityName} o where ('%all%' = :auteur or lower(o.auteur) like :auteur ) or ('%all%' = :titre or lower(o.titre) like :titre)")
	List<Ouvrage> findByTitreAuteur(@Param("titre") String titre,@Param("auteur")String auteur );

	List<Ouvrage> findByCategorieContains(String categorie);

	@Query("select distinct o from #{#entityName} o left join fetch o.exemplaires")
	List<Ouvrage> findAllWithExemplaires();

	@Query("select o from #{#entityName} o left join fetch o.exemplaires where o.id = ?1")
	Optional<Ouvrage> findByIdWithExemplaires(Long ouvrageId);

}