package fr.soro.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.soro.entities.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	@Query("select u from #{#entityName} u left join fetch u.roles left join fetch u.emprunts where u.username = ?1")
	User findByUsernameWithAll(String username);
}
