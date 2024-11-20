package com.hope.apiapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.hope.apiapp.service.PasswordResetTokenService;

@ExtendWith(MockitoExtension.class)
public class PasswordResetControllerTest {

	@InjectMocks
	private PasswordResetController passwordResetController;

	@Mock
	private PasswordResetTokenService passwordResetService;

	@Test
	public void testRequestPasswordReset_Success() {
		// Arrange
		String email = "abc@gmail.com";
		String message = "Reset token sent to email.";

		when(passwordResetService.generateResetToken(anyString(), anyBoolean())).thenReturn(message);

		// Act
		ResponseEntity<String> response = passwordResetController.requestPasswordReset(email);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).isEqualTo(message);
	}

	@Test
	public void testRequestPasswordReset_Exception() {
		// Arrange
		String email = "abc@gmail.com";

		when(passwordResetService.generateResetToken(anyString(), anyBoolean()))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> passwordResetController.requestPasswordReset(email))
				.isInstanceOf(RuntimeException.class).hasMessageContaining("Unexpected error");
	}
}
