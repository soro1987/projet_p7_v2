package fr.soro.repositories;

import fr.soro.entities.Ouvrage;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.soro.entities.Exemplaire;

import java.util.List;

public interface ExemplaireRepository extends JpaRepository<Exemplaire, Long> {
	public Exemplaire getExemplaireById( Long id );

//	public List<Exemplaire> findAllWhereBibliothequeId(Long biblioId);

//	public List<Exemplaire> findAllWhereBibliotheque(Long biblioId);

//	public List<Exemplaire> findByIdBibliotheque(Bibliotheque biblio, Long biblioId);

//	public List<Exemplaire> findByOuvrageIdGroupbyBibliotheque(Long biblioId);
	
	Long countByOuvrageIdAndBibliothequeNomAndDisponibleTrue(Long ouvrageId,String biblioId);

	// get all examplaire for particular ouvrage which is not available disponible
	public List<Exemplaire> findAllByOuvrageAndDisponible(Ouvrage ouvrage, boolean disponsible);
}
// after search button is cliecked, if library does not have bnook, it returns empty
// else if library has book the book image and details is shown and the user can click a button to
//  show its availability by library
//  if library dosen t have the book available -> AvailabilityDTO is returrned  Earlist return date, reservation list count