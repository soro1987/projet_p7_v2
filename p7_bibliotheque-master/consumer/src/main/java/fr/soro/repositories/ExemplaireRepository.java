package fr.soro.repositories;

import fr.soro.entities.Emprunt;
import fr.soro.entities.Ouvrage;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.soro.entities.Exemplaire;

import java.util.List;
import java.util.Optional;

public interface ExemplaireRepository extends JpaRepository<Exemplaire, Long> {
	public Exemplaire getExemplaireById(Long id);

	Long countByOuvrageIdAndBibliothequeNomAndDisponibleTrue(Long ouvrageId, String biblioId);

//	Optional<Exemplaire> findFirstByOuvrageIdOrderByEmpruntDateEcheanceAsc(Long ouvrageId);

	List<Exemplaire> findAllByOuvrageIdOrderByEmpruntDateEcheanceAsc(Long ouvrageId);

    Optional<Exemplaire> findFirstByOuvrageIdAndEmpruntNotNullOrderByEmpruntDateEcheanceAsc(Long ouvrageId);
}