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

import com.hope.apiapp.dto.PharmacistAddRequestDto;
import com.hope.apiapp.dto.PharmacistUpdateRequestDto;
import com.hope.apiapp.helper.ApiResponseSuccess;
import com.hope.apiapp.model.Pharmacist;
import com.hope.apiapp.service.PharmacistService;

@ExtendWith(MockitoExtension.class)
public class PharmacistControllerTest {

	@InjectMocks
	private PharmacistController pharmacistController;

	@Mock
	private PharmacistService pharmacistService;

	@Test
	public void testAddPharmacist_Success() {
		// Arrange
		PharmacistAddRequestDto request = new PharmacistAddRequestDto();
		Pharmacist updatedPharmacist = new Pharmacist();

		when(pharmacistService.addPharmacist(any(PharmacistAddRequestDto.class))).thenReturn(updatedPharmacist);

		// Act
		ResponseEntity<ApiResponseSuccess<Long>> response = pharmacistController.addPharmacist(request);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testAddPharmacist_Exception() {
		// Arrange
		PharmacistAddRequestDto request = new PharmacistAddRequestDto();

		when(pharmacistService.addPharmacist(any(PharmacistAddRequestDto.class)))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> pharmacistController.addPharmacist(request)).isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Unexpected error");
	}

	@Test
	public void testUpdatePharmacist_Success() {
		// Arrange
		Long id = 1L;
		PharmacistUpdateRequestDto request = new PharmacistUpdateRequestDto();
		Pharmacist updatedPharmacist = new Pharmacist();

		when(pharmacistService.updatePharmacist(eq(id), any(PharmacistUpdateRequestDto.class)))
				.thenReturn(updatedPharmacist);

		// Act
		ResponseEntity<ApiResponseSuccess<Long>> response = pharmacistController.updatePharmacist(id, request);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testUpdatePharmacist_Exception() {
		// Arrange
		Long id = 1L;
		PharmacistUpdateRequestDto request = new PharmacistUpdateRequestDto();

		when(pharmacistService.updatePharmacist(eq(id), any(PharmacistUpdateRequestDto.class)))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> pharmacistController.updatePharmacist(id, request))
				.isInstanceOf(RuntimeException.class).hasMessageContaining("Unexpected error");
	}

}
