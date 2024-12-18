package com.hope.apiapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hope.apiapp.model.PasswordResetToken;
import com.hope.apiapp.service.PasswordResetTokenService;
import com.hope.apiapp.service.PharmacistService;

@RestController
@RequestMapping("/api/auth")
@Validated
public class PasswordUpdateController {

	private static final Logger logger = LoggerFactory.getLogger(PasswordUpdateController.class);

	@Autowired
	private PasswordResetTokenService passwordResetService;

	@Autowired
	private PharmacistService pharmacistService;

	@PostMapping("/v1/reset-password")
	public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {

		PasswordResetToken resetToken = passwordResetService.validatePasswordResetToken(token);

		if (resetToken != null) {
			try {
				Long userId = resetToken.getUserId();

				boolean resetResult = pharmacistService.resetPassword(userId, newPassword);

				if (resetResult) {
					passwordResetService.invalidateToken(resetToken); // Invalidate token after use

					return ResponseEntity.ok("Password reset successful.");
				} else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token.");
				}
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token.");
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token.");
		}
	}
}
