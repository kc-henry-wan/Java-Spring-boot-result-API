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

import com.hope.apiapp.dto.PharmacyBranchRequestDto;

import com.hope.apiapp.helper.ApiResponseSuccess;
import com.hope.apiapp.model.PharmacyBranch;
import com.hope.apiapp.service.PharmacyBranchService;

@ExtendWith(MockitoExtension.class)
public class PharmacyBranchControllerTest {

	@InjectMocks
	private PharmacyBranchController pharmacyBranchController;

	@Mock
	private PharmacyBranchService pharmacyBranchService;

	@Test
	public void testAddPharmacyBranch_Success() {
		// Arrange
		PharmacyBranchRequestDto request = new PharmacyBranchRequestDto();
		PharmacyBranch updatedPharmacyBranch = new PharmacyBranch();

		when(pharmacyBranchService.addPharmacyBranch(any(PharmacyBranchRequestDto.class))).thenReturn(updatedPharmacyBranch);

		// Act
		ResponseEntity<ApiResponseSuccess<Long>> response = pharmacyBranchController.addPharmacyBranch(request);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testAddPharmacyBranch_Exception() {
		// Arrange
		PharmacyBranchRequestDto request = new PharmacyBranchRequestDto();

		when(pharmacyBranchService.addPharmacyBranch(any(PharmacyBranchRequestDto.class)))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> pharmacyBranchController.addPharmacyBranch(request)).isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Unexpected error");
	}

	@Test
	public void testUpdatePharmacyBranch_Success() {
		// Arrange
		Long id = 1L;
		PharmacyBranchRequestDto request = new PharmacyBranchRequestDto();
		PharmacyBranch updatedPharmacyBranch = new PharmacyBranch();

		when(pharmacyBranchService.updatePharmacyBranch(eq(id), any(PharmacyBranchRequestDto.class)))
				.thenReturn(updatedPharmacyBranch);

		// Act
		ResponseEntity<ApiResponseSuccess<Long>> response = pharmacyBranchController.updatePharmacyBranch(id, request);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testUpdatePharmacyBranch_Exception() {
		// Arrange
		Long id = 1L;
		PharmacyBranchRequestDto request = new PharmacyBranchRequestDto();

		when(pharmacyBranchService.updatePharmacyBranch(eq(id), any(PharmacyBranchRequestDto.class)))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> pharmacyBranchController.updatePharmacyBranch(id, request))
				.isInstanceOf(RuntimeException.class).hasMessageContaining("Unexpected error");
	}

}
