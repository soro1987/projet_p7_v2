package fr.soro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.soro.entities.Bibliotheque;

@Repository
public interface BibliothequeRepository extends JpaRepository<Bibliotheque, Long> {
	List<Bibliotheque> findByNom(String nom);
}