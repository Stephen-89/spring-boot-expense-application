package com.stephen.expense.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stephen.expense.dto.AuthModel;
import com.stephen.expense.dto.ImageDto;
import com.stephen.expense.dto.MfaDto;
import com.stephen.expense.entity.User;
import com.stephen.expense.service.AuthService;
import com.stephen.expense.service.TotpService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
	
	private AuthService authService;
	private TotpService totpService;
	
	public AuthController(AuthService authService, TotpService totpService) {
		this.authService = authService;
		this.totpService = totpService;
	}

	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody AuthModel authModel) throws Exception {
		return authService.loginUser(authModel);
	}

	@GetMapping("/generate-qr-image")
	public ResponseEntity<ImageDto> generateUriForImage() {
		return new ResponseEntity<>(new ImageDto(totpService.generateUriForImage()), HttpStatus.CREATED);
	}

	@PostMapping("/disable-mfa")
	public void disableMfa() {
		totpService.disableMfa();
	}

	@PostMapping("/verfiy-code")
	public ResponseEntity<Object> verifyCode(@Valid @RequestBody MfaDto mfaDto) {
		return new ResponseEntity<>(totpService.verifyCode(mfaDto), HttpStatus.OK);
	}
	
}


















