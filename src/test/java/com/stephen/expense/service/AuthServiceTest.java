package com.stephen.expense.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;

import com.stephen.expense.dto.AuthModel;
import com.stephen.expense.entity.User;
import com.stephen.expense.exceptions.ResourceNotFoundException;
import com.stephen.expense.exceptions.TotpRequiredException;
import com.stephen.expense.exceptions.user.UserExpiredException;
import com.stephen.expense.exceptions.user.UserLockedException;
import com.stephen.expense.repository.UserRepository;
import com.stephen.expense.service.AuthService;
import com.stephen.expense.service.UserRolesDetailsService;
import com.stephen.expense.util.JwtTokenUtil;

@SpringBootTest
class AuthServiceTest {

    @MockBean
    private UserRolesDetailsService userRolesDetailsService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        when(userRolesDetailsService.loadUserByUsername(anyString())).thenReturn(mock(UserDetails.class));
        when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn("dummyToken");
    }

	@Test
    @SuppressWarnings("deprecation")
	void testLoginUserSuccess() throws Exception {
    	
    	AuthModel authModel = mockAuthModel();
        User user = mockUser();
        user.setMfaEnabled(false);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("username");

        when(userRolesDetailsService.checkUserNonExpired(anyString())).thenReturn(false);
        when(userRolesDetailsService.checkUserNonLocked(anyString())).thenReturn(false);
        when(userRepository.findByUsername(anyObject())).thenReturn(Optional.of(user));

        ResponseEntity<User> response = authService.loginUser(authModel);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("dummyToken", response.getBody().getAccessToken());
        
    }

    @Test
    void testLoginUserUserExpired() {
    	
    	AuthModel authModel = mockAuthModel();

        when(userRolesDetailsService.checkUserNonExpired(anyString())).thenReturn(true);

        Exception exception = assertThrows(UserExpiredException.class, () -> {
            authService.loginUser(authModel);
        });

        assertEquals("User Expired", exception.getMessage());
        
    }

    @Test
    void testLoginUserUserLocked() {
    	
    	AuthModel authModel = mockAuthModel();

        when(userRolesDetailsService.checkUserNonExpired(anyString())).thenReturn(false);
        when(userRolesDetailsService.checkUserNonLocked(anyString())).thenReturn(true);

        Exception exception = assertThrows(UserLockedException.class, () -> {
            authService.loginUser(authModel);
        });

        assertEquals("User Locked", exception.getMessage());
        
    }

	@Test
    @SuppressWarnings("deprecation")
	void testLoginUserUserNotFound() {
    	
    	AuthModel authModel = mockAuthModel();

        when(userRolesDetailsService.checkUserNonExpired(anyString())).thenReturn(false);
        when(userRolesDetailsService.checkUserNonLocked(anyString())).thenReturn(false);
        when(userRepository.findByUsername(anyObject())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            authService.loginUser(authModel);
        });

        assertEquals("User Not Found", exception.getMessage());
        
    }

	@Test
    @SuppressWarnings("deprecation")
	void testLoginUserMfaRequired() {
    	
    	AuthModel authModel = mockAuthModel();
    	User user = mockUser();
    	user.setMfaEnabled(true);
    	
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("username");

        when(userRolesDetailsService.checkUserNonExpired(anyString())).thenReturn(false);
        when(userRolesDetailsService.checkUserNonLocked(anyString())).thenReturn(false);
        when(userRepository.findByUsername(anyObject())).thenReturn(Optional.of(user));

        Exception exception = assertThrows(TotpRequiredException.class, () -> {
            authService.loginUser(authModel);
        });

        assertEquals("Multi-Factor Authentication Required", exception.getMessage());
        
    }

	private User mockUser() {
		User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
		return user;
	}

	private AuthModel mockAuthModel() {
        AuthModel authModel = new AuthModel();
        authModel.setUsername("testuser");
        authModel.setPassword("testpass");
		return authModel;
	}
    
}
