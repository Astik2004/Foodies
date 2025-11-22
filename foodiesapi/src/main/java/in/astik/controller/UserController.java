package in.astik.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.astik.io.UserRequest;
import in.astik.io.UserResponse;
import in.astik.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	private final UserService userService;
	public UserController(UserService userService)
	{
		this.userService=userService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserResponse> register(@RequestBody UserRequest user)
	{
		UserResponse registeredUser=userService.registerUser(user);
		return new ResponseEntity<UserResponse>(registeredUser, HttpStatus.CREATED);
	}

}
