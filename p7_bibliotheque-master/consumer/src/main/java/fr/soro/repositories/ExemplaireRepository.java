package fr.soro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.soro.entities.Exemplaire;

public interface ExemplaireRepository extends JpaRepository<Exemplaire, Long> {
	public Exemplaire getExemplaireById( Long id );

//	public List<Exemplaire> findAllWhereBibliothequeId(Long biblioId);

//	public List<Exemplaire> findAllWhereBibliotheque(Long biblioId);

//	public List<Exemplaire> findByIdBibliotheque(Bibliotheque biblio, Long biblioId);

//	public List<Exemplaire> findByOuvrageIdGroupbyBibliotheque(Long biblioId);
	
	Long countByOuvrageIdAndBibliothequeNomAndDisponibleTrue(Long ouvrageId,String biblioId);
}
