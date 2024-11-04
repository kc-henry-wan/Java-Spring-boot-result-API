package com.hope.apiapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hope.apiapp.service.PasswordResetTokenService;

@RestController
@RequestMapping("/api/auth")
@Validated
public class PasswordResetController {

	private static final Logger logger = LoggerFactory.getLogger(PasswordResetController.class);

	@Autowired
	private PasswordResetTokenService passwordResetService;

	@PostMapping("/v1/request-password-reset")
	public ResponseEntity<String> requestPasswordReset(@RequestParam String email) {
		String response = passwordResetService.generateResetToken(email);
		return ResponseEntity.ok(response);
	}
}
