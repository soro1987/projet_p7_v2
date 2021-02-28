package fr.soro.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.soro.entities.Emprunt;
import fr.soro.entities.User;
import fr.soro.repositories.UserRepository;

@Service
public class UserService {
	
	@Resource
	@Autowired
	private UserRepository userRepository;
	@Autowired
	EmpruntService empruntService;
	
	public User getUser(Long id) {
		return this.userRepository.getOne(id);
	}
	
	public User getUserByUsername(String username) {
		return this.userRepository.findByUsername( username);
	}
	
	public List<User> getAllUser() {
		return this.userRepository.findAll();
	}
	
	public List<User> getUsersEmpruntExpire(List<Emprunt> expireEmprunts) {
		List<User> usersEmpruntExpire = new ArrayList<User>();
		for(Emprunt empExpire: expireEmprunts)  {
			usersEmpruntExpire.add(empExpire.getUser());
		}
	return usersEmpruntExpire;
	}
	
	
	
	public User updateUser(Long id, User user) {
		User userUpdated = this.getUser(id);
		userUpdated.setNom(user.getNom());
		userUpdated.setPrenom(user.getPrenom());
		userUpdated.setEmail(user.getEmail());
		userUpdated.setPassword(user.getPassword());
		userUpdated.setTelephone(user.getTelephone());
		return userUpdated;
	}
	
	public void deleteUser(Long id) {
		 this.userRepository.deleteById(id);
	}
	
	public User save(User user) {
		return this.userRepository.save(user);
	}

}
