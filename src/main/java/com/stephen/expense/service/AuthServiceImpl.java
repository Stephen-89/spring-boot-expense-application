package com.stephen.expense.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.stephen.expense.dto.AuthModel;
import com.stephen.expense.entity.User;
import com.stephen.expense.exceptions.ResourceNotFoundException;
import com.stephen.expense.exceptions.TotpRequiredException;
import com.stephen.expense.exceptions.user.UserDetailsException;
import com.stephen.expense.exceptions.user.UserDisabledException;
import com.stephen.expense.exceptions.user.UserExpiredException;
import com.stephen.expense.exceptions.user.UserLockedException;
import com.stephen.expense.repository.UserRepository;
import com.stephen.expense.util.JwtTokenUtil;

@Service
public class AuthServiceImpl implements AuthService {

	private AuthenticationManager authenticationManager;
	private UserRolesDetailsService userRolesDetailsService;
	private UserRepository userRepository;
	private JwtTokenUtil jwtTokenUtil;

	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRolesDetailsService userRolesDetailsService,
			UserRepository userRepository, JwtTokenUtil jwtTokenUtil) {
		this.authenticationManager = authenticationManager;
		this.userRolesDetailsService = userRolesDetailsService;
		this.userRepository = userRepository;
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@Override
	public ResponseEntity<User> loginUser(AuthModel authModel) throws Exception {

		authenticate(authModel.getUsername(), authModel.getPassword());

		if (Boolean.FALSE.equals(userRolesDetailsService.checkUserNonExpired(authModel.getUsername()))) {

			if (Boolean.FALSE.equals(userRolesDetailsService.checkUserNonLocked(authModel.getUsername()))) {

				final UserDetails userDetails = userRolesDetailsService.loadUserByUsername(authModel.getUsername());

				Optional<User> user = userRepository.findByUsername(userDetails.getUsername());

				if(user.isPresent()) {

					if (Boolean.TRUE.equals(user.get().getMfaEnabled())) {
						throw new TotpRequiredException("Multi-Factor Authentication Required");
					} else {

						final String token = jwtTokenUtil.generateToken(userDetails);
						user.get().setAccessToken(token);
						return new ResponseEntity<>(user.get(), HttpStatus.OK);

					}

				} else {
					throw new ResourceNotFoundException("User Not Found");
				}
				
			} else {
				throw new UserLockedException("User Locked");
			}

		} else {
			throw new UserExpiredException("User Expired");
		}

	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new UserDisabledException("User disabled");
		} catch (BadCredentialsException e) {
			throw new UserDetailsException("Bad Credentials");
		}
	}

}

























