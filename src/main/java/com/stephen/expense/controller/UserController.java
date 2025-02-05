package com.stephen.expense.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stephen.expense.dto.UserDto;
import com.stephen.expense.entity.User;
import com.stephen.expense.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<User> save(@Valid @RequestBody UserDto user) {
		return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
	}

	@GetMapping("/profile")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<User> readUser() {
		return new ResponseEntity<>(userService.getLoggedInUser(), HttpStatus.OK);
	}

	@PutMapping("/profile")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<User> updateUser(@RequestBody UserDto user) {
		return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
	}

	@DeleteMapping("/deactivate")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<HttpStatus> deleteUser() {
		userService.deleteUser();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
