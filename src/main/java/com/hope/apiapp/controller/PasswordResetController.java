package com.hope.apiapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hope.apiapp.service.PasswordResetTokenService;

@RestController
@RequestMapping("/api")
@Validated
public class PasswordResetController {

	@Autowired
	private PasswordResetTokenService passwordResetService;

	@PostMapping("/auth/v1/request-password-reset")
	public ResponseEntity<String> requestPasswordReset(@RequestParam String email) {
		String response = passwordResetService.generateResetToken(email, false);
		return ResponseEntity.ok(response);
	}
}
