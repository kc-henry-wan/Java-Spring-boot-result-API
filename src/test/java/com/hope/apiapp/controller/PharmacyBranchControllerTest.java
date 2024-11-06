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
	public void testFindAllBranches_Success() {
		// Arrange
		Pageable pageable = PageRequest.of(0, 10);
		PharmacyBranch returnedPharmacyBranch = new PharmacyBranch();
		List<PharmacyBranch> myList = List.of(returnedPharmacyBranch);
		Page<PharmacyBranch> myPage = new PageImpl<>(myList, pageable, myList.size());

		when(pharmacyBranchService.findByStatus(isNull(), any(Pageable.class))).thenReturn(myPage);

		// Act
		ResponseEntity<ApiResponseSuccess<Page<PharmacyBranch>>> response = pharmacyBranchController.getAllBranches(0,
				10, "status", "asc", null);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testFindAllBranchesByStatus_Success() {
		// Arrange
		String status = "Open";
		Pageable pageable = PageRequest.of(0, 10);
		PharmacyBranch returnedPharmacyBranch = new PharmacyBranch();
		List<PharmacyBranch> myList = List.of(returnedPharmacyBranch);
		Page<PharmacyBranch> myPage = new PageImpl<>(myList, pageable, myList.size());

		when(pharmacyBranchService.findByStatus(anyString(), any(Pageable.class))).thenReturn(myPage);

		// Act
		ResponseEntity<ApiResponseSuccess<Page<PharmacyBranch>>> response = pharmacyBranchController.getAllBranches(0,
				10, "status", "asc", status);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testFindAllBranches_Exception() {

		when(pharmacyBranchService.findByStatus(isNull(), any(Pageable.class)))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> pharmacyBranchController.getAllBranches(0, 10, "status", "asc", null))
				.isInstanceOf(RuntimeException.class).hasMessageContaining("Unexpected error");
	}

	@Test
	public void testGetPharmacyBranchByIds_Success() {
		// Arrange
		Long id = 1L;
		PharmacyBranch newPharmacyBranch = new PharmacyBranch();

		when(pharmacyBranchService.findBranchById(id)).thenReturn(newPharmacyBranch);

		// Act
		ResponseEntity<ApiResponseSuccess<PharmacyBranch>> response = pharmacyBranchController.findBranchById(id);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testGetPharmacyBranchByIds_Exception() {
		// Arrange
		Long id = 1L;

		when(pharmacyBranchService.findBranchById(id)).thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> pharmacyBranchController.findBranchById(id)).isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Unexpected error");
	}

	@Test
	public void testAddPharmacyBranch_Success() {
		// Arrange
		PharmacyBranchRequestDto request = new PharmacyBranchRequestDto();
		PharmacyBranch updatedPharmacyBranch = new PharmacyBranch();

		when(pharmacyBranchService.addPharmacyBranch(any(PharmacyBranchRequestDto.class)))
				.thenReturn(updatedPharmacyBranch);

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
		assertThatThrownBy(() -> pharmacyBranchController.addPharmacyBranch(request))
				.isInstanceOf(RuntimeException.class).hasMessageContaining("Unexpected error");
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
