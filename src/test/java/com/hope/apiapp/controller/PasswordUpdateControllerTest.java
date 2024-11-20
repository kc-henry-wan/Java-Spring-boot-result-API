package com.hope.apiapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.hope.apiapp.model.PasswordResetToken;
import com.hope.apiapp.service.PasswordResetTokenService;
import com.hope.apiapp.service.PharmacistService;

@ExtendWith(MockitoExtension.class)
public class PasswordUpdateControllerTest {

	@InjectMocks
	private PasswordUpdateController passwordUpdateController;

	@Mock
	private PasswordResetTokenService passwordResetService;

	@Mock
	private PharmacistService pharmacistService;

	@Test
	public void testResetPassword_Success() {
		// Arrange
		String token = "abc";
		String newPassword = "pwpw";
		String message = "Password reset successful.";
		PasswordResetToken resetToken = new PasswordResetToken();

		when(passwordResetService.validatePasswordResetToken(anyString())).thenReturn(resetToken);
		when(pharmacistService.resetPassword(isNull(), anyString())).thenReturn(true);

		// Act
		ResponseEntity<String> response = passwordUpdateController.resetPassword(token, newPassword);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).isEqualTo(message);
	}

	@Test
	public void testResetPassword_TokenException() {
		// Arrange
		String token = "abc";
		String newPassword = "pwpw";

		when(passwordResetService.validatePasswordResetToken(anyString()))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> passwordUpdateController.resetPassword(token, newPassword))
				.isInstanceOf(RuntimeException.class).hasMessageContaining("Unexpected error");
	}

	@Test
	public void testResetPassword_Exception() {
		// Arrange
		String token = "abc";
		String newPassword = "pwpw";
		PasswordResetToken resetToken = new PasswordResetToken();
		String error = "Invalid or expired token.";

		when(passwordResetService.validatePasswordResetToken(anyString())).thenReturn(resetToken);
		when(pharmacistService.resetPassword(isNull(), anyString()))
				.thenThrow(new RuntimeException("Unexpected error"));

//		// Act & Assert
//		assertThatThrownBy(() -> passwordUpdateController.resetPassword(token, newPassword))
//				.isInstanceOf(RuntimeException.class).hasMessageContaining("Unexpected error");
		// Act
		ResponseEntity<String> response = passwordUpdateController.resetPassword(token, newPassword);

		// Assert
		assertThat(response.getStatusCode().is4xxClientError()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).isEqualTo(error);

	}

}
