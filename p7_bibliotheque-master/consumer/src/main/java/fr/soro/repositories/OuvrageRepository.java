package fr.soro.repositories;

import fr.soro.entities.Ouvrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface OuvrageRepository extends JpaRepository<Ouvrage, Long> {

	List<Ouvrage> findByTitreContains(String titre);
	
	List<Ouvrage> findByTitre(String titre);

	List<Ouvrage> findByAuteur(String auteur);
	
	List<Ouvrage> findByDateParution(Date parution);


	
	@Query("Select o from Ouvrage o where ('%all%' = :auteur or o.auteur like :auteur ) or ('%all%' = :titre or o.titre like :titre)")
	List<Ouvrage> findByTitreAuteur(@Param("titre") String titre,@Param("auteur")String auteur );


	/*@Query("Select o from Ouvrage o where ('%all%' = :categorie or o.categorie like :categorie )")
	List<Ouvrage> findByCategorie(@Param("categorie") String categorie );*/

	List<Ouvrage> findByCategorieContains(String categorie);


}