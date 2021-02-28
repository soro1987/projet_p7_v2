package controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import fr.soro.entities.User;
import fr.soro.service.UserService;
@CrossOrigin("*")
@RestController
public class UserController
{
	
	
	@Resource
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    @GetMapping("/user")
    public List<User> getAll()
    {
        return userService.getAllUser();
    }
    
    @GetMapping(value = "/user/{username}")
	public ResponseEntity<User> getBytitre(@PathVariable(value = "username") String username) {
		User user = userService.getUserByUsername(username);
		return new ResponseEntity<User>(user, HttpStatus.FOUND);
	}
    
    @PostMapping(value ="/signup",consumes = "application/json", produces = "application/json")
    public void signUp(@RequestBody User user)
    {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.save(user);
    }
}