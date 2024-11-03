package com.hope.apiapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;

import com.hope.apiapp.dto.PharmacistRequest;
import com.hope.apiapp.model.Pharmacist;
import com.hope.apiapp.service.PharmacistService;
import com.hope.apiapp.util.ApiResponseSuccess;

@ExtendWith(MockitoExtension.class)
public class PharmacistControllerTest {

	@InjectMocks
	private PharmacistController pharmacistController;

	@Mock
	private PharmacistService pharmacistService;

	@Test
	public void testUpdatePharmacist_Success() {
		// Arrange
		Long id = 1L;
		PharmacistRequest request = new PharmacistRequest();
		request.setPassword("newpassword");
		request.setEmail("test@example.com");
		request.setFirstName("UpdatedFirstName");
		request.setLastName("UpdatedLastName");
		request.setMobile("1234567890");
		request.setAddress1("123 Updated St");
		request.setPostalCode("12345");

		Pharmacist updatedPharmacist = new Pharmacist();
		updatedPharmacist.setPharmacistId(id);
		updatedPharmacist.setEmail(request.getEmail());
		updatedPharmacist.setFirstName(request.getFirstName());
		updatedPharmacist.setLastName(request.getLastName());
		updatedPharmacist.setMobile(request.getMobile());
		updatedPharmacist.setAddress1(request.getAddress1());
		updatedPharmacist.setPostalCode(request.getPostalCode());
		updatedPharmacist.setUpdatedAt(LocalDateTime.now());

		when(pharmacistService.updatePharmacist(eq(id), any(PharmacistRequest.class))).thenReturn(updatedPharmacist);

		// Act
		ResponseEntity<ApiResponseSuccess<Long>> response = pharmacistController.updatePharmacist(id, request);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
//		assertThat(response.getBody().getApiStatus()).isEqualTo(updatedPharmacist);
//		assertThat(response.).isEqualTo("Success");
	}

	@Test
	public void testUpdatePharmacist_InvalidInput() {
		// Arrange
		Long id = 1L;
		PharmacistRequest request = new PharmacistRequest();
		request.setEmail("invalid-email"); // Invalid email format
		request.setPassword(""); // Blank password, which is not allowed

		// Act & Assert
		assertThatThrownBy(() -> pharmacistController.updatePharmacist(id, request)).isInstanceOf(BindException.class)
				.hasMessageContaining("Invalid input data");
	}

	@Test
	public void testUpdatePharmacist_NotFound() {
		// Arrange
		Long id = 1L;
		PharmacistRequest request = new PharmacistRequest();
		request.setPassword("newpassword");
		request.setEmail("test@example.com");
		request.setFirstName("FirstName");
		request.setLastName("LastName");
		request.setMobile("1234567890");
		request.setAddress1("123 Main St");
		request.setPostalCode("12345");

		when(pharmacistService.updatePharmacist(eq(id), any(PharmacistRequest.class))).thenReturn(null);

		// Act
		ResponseEntity<ApiResponseSuccess<Long>> response = pharmacistController.updatePharmacist(id, request);

		// Assert
		assertThat(response.getStatusCode().is4xxClientError()).isTrue();
		assertThat(response.getBody()).isNotNull();
//		assertThat(response.getBody().getApiStatus()).isEqualTo("Error");
//		assertThat(response.getBody().getErrorCode()).isEqualTo("NOT_FOUND");
	}

	@Test
	public void testUpdatePharmacist_Exception() {
		// Arrange
		Long id = 1L;
		PharmacistRequest request = new PharmacistRequest();
		request.setPassword("newpassword");
		request.setEmail("test@example.com");
		request.setFirstName("FirstName");
		request.setLastName("LastName");
		request.setMobile("1234567890");
		request.setAddress1("123 Main St");
		request.setPostalCode("12345");

		when(pharmacistService.updatePharmacist(eq(id), any(PharmacistRequest.class)))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> pharmacistController.updatePharmacist(id, request))
				.isInstanceOf(RuntimeException.class).hasMessageContaining("Unexpected error");
	}

}
