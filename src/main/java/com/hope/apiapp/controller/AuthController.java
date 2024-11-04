package com.hope.apiapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hope.apiapp.dto.JwtAuthenticationResponse;
import com.hope.apiapp.dto.LoginRequest;
import com.hope.apiapp.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@PostMapping("/v1/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

		try {
			logger.info("authenticateUser:" + loginRequest.getUsername() + " , " + loginRequest.getPassword());
			// Create authentication token
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			logger.info("authenticateUser: successful");
			// Set authentication in the security context
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Generate JWT token
			String jwtToken = jwtTokenProvider.generateToken(loginRequest.getUsername());
			logger.info("authenticateUser: token generated:" + jwtToken);

			return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken));

		} catch (BadCredentialsException ex) {
			throw new BadCredentialsException("Invalid username or password");
		}
	}
}
