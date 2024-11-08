package com.hope.apiapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	public void testGetNegotiation_Success() {
		// Arrange
		Pageable pageable = PageRequest.of(0, 10);
		Negotiation returnedNegotiation = new Negotiation();
		List<Negotiation> myList = List.of(returnedNegotiation);
		Page<Negotiation> myPage = new PageImpl<>(myList, pageable, myList.size());

		when(negotiationService.findByStatus(isNull(), any(Pageable.class))).thenReturn(myPage);

		// Act
		ResponseEntity<ApiResponseSuccess<Page<Negotiation>>> response = negotiationController.getAllNegotiations(0, 10,
				"status", "asc", null);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testGetNegotiationByStatus_Success() {
		// Arrange
		String status = "Open";
		Pageable pageable = PageRequest.of(0, 10);
		Negotiation returnedNegotiation = new Negotiation();
		List<Negotiation> myList = List.of(returnedNegotiation);
		Page<Negotiation> myPage = new PageImpl<>(myList, pageable, myList.size());

		when(negotiationService.findByStatus(anyString(), any(Pageable.class))).thenReturn(myPage);

		// Act
		ResponseEntity<ApiResponseSuccess<Page<Negotiation>>> response = negotiationController.getAllNegotiations(0, 10,
				"status", "asc", status);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testGetNegotiation_Exception() {
		when(negotiationService.findByStatus(isNull(), any(Pageable.class)))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> negotiationController.getAllNegotiations(0, 10, "status", "asc", null))
				.isInstanceOf(RuntimeException.class).hasMessageContaining("Unexpected error");
	}

	@Test
	public void testGetNegotiationByIds_Success() {
		// Arrange
		Long id = 1L;
		Negotiation newNegotiation = new Negotiation();

		when(negotiationService.getNegotiationById(id)).thenReturn(newNegotiation);

		// Act
		ResponseEntity<ApiResponseSuccess<Negotiation>> response = negotiationController.getNegotiationById(id);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testGetNegotiationByIds_Exception() {
		// Arrange
		Long id = 1L;

		when(negotiationService.getNegotiationById(id)).thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> negotiationController.getNegotiationById(id)).isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Unexpected error");
	}

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
