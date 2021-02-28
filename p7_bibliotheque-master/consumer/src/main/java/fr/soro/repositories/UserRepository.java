package fr.soro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.soro.entities.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public User findByUsername(String username);
}
