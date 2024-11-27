package com.hope.apiapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hope.apiapp.dto.JwtAuthenticationResponse;
import com.hope.apiapp.dto.LoginRequest;
import com.hope.apiapp.helper.ApiResponseFail;
import com.hope.apiapp.helper.ApiResponseSuccess;
import com.hope.apiapp.security.CustomUserDetails;
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

			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
			if ("ACTIVE".equals(userDetails.getStatus().toUpperCase())) {

				// Generate JWT token
				String jwtToken = jwtTokenProvider.generateToken(loginRequest.getUsername());

				JwtAuthenticationResponse response = new JwtAuthenticationResponse(jwtToken, userDetails.getUserId());

				return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", response), HttpStatus.OK);
			} else {

				return new ResponseEntity<>(
						new ApiResponseFail("L001", "Please check email and activate your account before login"),
						HttpStatus.FORBIDDEN);
			}

		} catch (BadCredentialsException ex) {
			throw new BadCredentialsException("Invalid username or password");
		}
	}
}
