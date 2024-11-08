package com.hope.apiapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
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

import com.hope.apiapp.dto.PharmacistAddRequestDto;
import com.hope.apiapp.dto.PharmacistProjection;
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
	public void testGetPharmacist_Success() {
		// Arrange
		Pageable pageable = PageRequest.of(0, 10);
		PharmacistProjection mockPharmacist = mock(PharmacistProjection.class);
		List<PharmacistProjection> myList = List.of(mockPharmacist);
		Page<PharmacistProjection> myPage = new PageImpl<>(myList, pageable, myList.size());

		when(pharmacistService.findByStatusWithLimitedFields(isNull(), any(Pageable.class))).thenReturn(myPage);

		// Act
		ResponseEntity<ApiResponseSuccess<Page<PharmacistProjection>>> response = pharmacistController
				.getAllPharmacists(0, 10, "status", "asc", null);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testGetPharmacistByStatus_Success() {
		// Arrange
		String status = "Open";
		Pageable pageable = PageRequest.of(0, 10);
		PharmacistProjection mockPharmacist = mock(PharmacistProjection.class);
		List<PharmacistProjection> myList = List.of(mockPharmacist);
		Page<PharmacistProjection> myPage = new PageImpl<>(myList, pageable, myList.size());

		when(pharmacistService.findByStatusWithLimitedFields(anyString(), any(Pageable.class))).thenReturn(myPage);

		// Act
		ResponseEntity<ApiResponseSuccess<Page<PharmacistProjection>>> response = pharmacistController
				.getAllPharmacists(0, 10, "status", "asc", status);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testGetPharmacist_Exception() {
		when(pharmacistService.findByStatusWithLimitedFields(isNull(), any(Pageable.class)))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> pharmacistController.getAllPharmacists(0, 10, "status", "asc", null))
				.isInstanceOf(RuntimeException.class).hasMessageContaining("Unexpected error");
	}

	@Test
	public void testGetPharmacistByIds_Success() {
		// Arrange
		Long id = 1L;
		Pharmacist newPharmacist = new Pharmacist();

		when(pharmacistService.getPharmacistById(id)).thenReturn(newPharmacist);

		// Act
		ResponseEntity<ApiResponseSuccess<Pharmacist>> response = pharmacistController.getPharmacistById(id);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
		assertThat(response.getBody().getData().getPassword()).isNull();
	}

	@Test
	public void testGetPharmacistByIds_Exception() {
		// Arrange
		Long id = 1L;

		when(pharmacistService.getPharmacistById(id)).thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> pharmacistController.getPharmacistById(id)).isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Unexpected error");
	}

	@Test
	public void testGetPharmacistByIdsWithLimitedFields_Success() {
		// Arrange
		Long id = 1L;
		PharmacistProjection mockPharmacist = mock(PharmacistProjection.class);

		when(pharmacistService.getPharmacistByIdWithLimitedFields(id)).thenReturn(mockPharmacist);

		// Act
		ResponseEntity<ApiResponseSuccess<PharmacistProjection>> response = pharmacistController
				.getPharmacistByIdWithLimitedFields(id);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
		assertThat(response.getBody().getData().getAddress2()).isNull();
	}

	@Test
	public void testGetPharmacistByIdsWithLimitedFields_Exception() {
		// Arrange
		Long id = 1L;

		when(pharmacistService.getPharmacistByIdWithLimitedFields(id))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> pharmacistController.getPharmacistByIdWithLimitedFields(id))
				.isInstanceOf(RuntimeException.class).hasMessageContaining("Unexpected error");
	}

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
