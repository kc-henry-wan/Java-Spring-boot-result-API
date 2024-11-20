package com.hope.apiapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
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

import com.hope.apiapp.dto.NegotiationAcceptRequestDto;
import com.hope.apiapp.dto.NegotiationAddRequestDto;
import com.hope.apiapp.dto.NegotiationDto;
import com.hope.apiapp.dto.NegotiationProjection;
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
		Long id = 1L;
		String status = "New";
		Pageable pageable = PageRequest.of(0, 10);
		NegotiationDto returnedNegotiation = new NegotiationDto();

		List<NegotiationDto> myList = List.of(returnedNegotiation);
		Page<NegotiationDto> myPage = new PageImpl<>(myList, pageable, myList.size());

		when(negotiationService.getNegotiations(any(Pageable.class), anyString(), anyLong())).thenReturn(myPage);

		// Act
		ResponseEntity<ApiResponseSuccess<Page<NegotiationDto>>> response = negotiationController.getNegotiations(0, 10,
				status, id);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testGetNegotiation_Exception() {
		when(negotiationService.getNegotiations(any(Pageable.class), isNull(), anyLong()))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> negotiationController.getNegotiations(0, 10, null, 0L))
				.isInstanceOf(RuntimeException.class).hasMessageContaining("Unexpected error");
	}

	@Test
	public void testGetNegotiationByIds_Success() {
		// Arrange
		Long id = 1L;
		NegotiationProjection newNegotiation = mock(NegotiationProjection.class);

		when(negotiationService.getNegotiationById(id)).thenReturn(newNegotiation);

		// Act
		ResponseEntity<ApiResponseSuccess<NegotiationProjection>> response = negotiationController
				.getNegotiationById(id);

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
		Long id = 77L;
		NegotiationAddRequestDto request = new NegotiationAddRequestDto();
		Negotiation updatedNegotiation = new Negotiation();
		updatedNegotiation.setNegotiationId(id);

		when(negotiationService.addNegotiation(any(NegotiationAddRequestDto.class))).thenReturn(updatedNegotiation);

		// Act
		ResponseEntity<ApiResponseSuccess<Long>> response = negotiationController.addNegotiation(request);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
		assertThat(response.getBody().getData()).isEqualTo(id);
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
	public void testAcceptNegotiation_Success() {
		// Arrange
		Long id = 77L;
		NegotiationAcceptRequestDto request = new NegotiationAcceptRequestDto();
		Negotiation updatedNegotiation = new Negotiation();
		updatedNegotiation.setNegotiationId(id);

		when(negotiationService.acceptNegotiation(eq(id), any(NegotiationAcceptRequestDto.class)))
				.thenReturn(updatedNegotiation);

		// Act
		ResponseEntity<ApiResponseSuccess<Long>> response = negotiationController.acceptNegotiation(id, request);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
		assertThat(response.getBody().getData()).isEqualTo(id);
	}

	@Test
	public void testAcceptNegotiationn_Exception() {
		// Arrange
		Long id = 1L;
		NegotiationAcceptRequestDto request = new NegotiationAcceptRequestDto();

		when(negotiationService.acceptNegotiation(eq(id), any(NegotiationAcceptRequestDto.class)))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> negotiationController.acceptNegotiation(id, request))
				.isInstanceOf(RuntimeException.class).hasMessageContaining("Unexpected error");
	}

	@Test
	public void testUpdateNegotiation_Success() {
		// Arrange
		Long id = 77L;
		NegotiationUpdateRequestDto request = new NegotiationUpdateRequestDto();
		Negotiation updatedNegotiation = new Negotiation();
		updatedNegotiation.setNegotiationId(id);

		when(negotiationService.updateNegotiation(eq(id), any(NegotiationUpdateRequestDto.class)))
				.thenReturn(updatedNegotiation);

		// Act
		ResponseEntity<ApiResponseSuccess<Long>> response = negotiationController.updateNegotiation(id, request);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
		assertThat(response.getBody().getData()).isEqualTo(id);
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
