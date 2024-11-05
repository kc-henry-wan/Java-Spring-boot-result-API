package com.hope.apiapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.hope.apiapp.dto.NegotiationAddRequestDto;
import com.hope.apiapp.dto.NegotiationUpdateRequestDto;
import com.hope.apiapp.helper.ApiResponseSuccess;
import com.hope.apiapp.model.Negotiation;
import com.hope.apiapp.service.NegotiationService;

@ExtendWith(MockitoExtension.class)
public class NegotiationControllerTest {

	@InjectMocks
	private NegotiationController negotiationController;

	@Mock
	private NegotiationService negotiationService;

	@Test
	public void testAddNegotiation_Success() {
		// Arrange
		NegotiationAddRequestDto request = new NegotiationAddRequestDto();
		Negotiation updatedNegotiation = new Negotiation();

		when(negotiationService.addNegotiation(any(NegotiationAddRequestDto.class))).thenReturn(updatedNegotiation);

		// Act
		ResponseEntity<ApiResponseSuccess<Long>> response = negotiationController.addNegotiation(request);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testAddNegotiation_Exception() {
		// Arrange
		NegotiationAddRequestDto request = new NegotiationAddRequestDto();

		when(negotiationService.addNegotiation(any(NegotiationAddRequestDto.class)))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> negotiationController.addNegotiation(request)).isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Unexpected error");
	}

	@Test
	public void testUpdateNegotiation_Success() {
		// Arrange
		Long id = 1L;
		NegotiationUpdateRequestDto request = new NegotiationUpdateRequestDto();
		Negotiation updatedNegotiation = new Negotiation();

		when(negotiationService.updateNegotiation(eq(id), any(NegotiationUpdateRequestDto.class)))
				.thenReturn(updatedNegotiation);

		// Act
		ResponseEntity<ApiResponseSuccess<Long>> response = negotiationController.updateNegotiation(id, request);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testUpdateNegotiation_Exception() {
		// Arrange
		Long id = 1L;
		NegotiationUpdateRequestDto request = new NegotiationUpdateRequestDto();

		when(negotiationService.updateNegotiation(eq(id), any(NegotiationUpdateRequestDto.class)))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> negotiationController.updateNegotiation(id, request))
				.isInstanceOf(RuntimeException.class).hasMessageContaining("Unexpected error");
	}

}