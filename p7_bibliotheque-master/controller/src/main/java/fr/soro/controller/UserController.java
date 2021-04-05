package fr.soro.controller;


import java.util.List;

import javax.annotation.Resource;

import fr.soro.dto.UserDto;
import fr.soro.mapper.UserMapper;
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
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, UserMapper userMapper)
    {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
    }
    
    @GetMapping("/user")
    public List<User> getAll()
    {
        return userService.getAllUser();
    }
    
    @GetMapping(value = "/user/{username}")
	public ResponseEntity<UserDto> getBytitre(@PathVariable(value = "username") String username) {
		User user = userService.getUserByUsername(username);
		UserDto userDto = userMapper.from(user);
		return ResponseEntity.ok(userDto);
	}

    @PostMapping(value ="/signup",consumes = "application/json", produces = "application/json")
    public void signUp(@RequestBody User user)
    {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.save(user);
    }
}