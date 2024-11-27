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

import com.hope.apiapp.dto.PharmacistAddRequestDto;
import com.hope.apiapp.dto.PharmacistDto;
import com.hope.apiapp.dto.PharmacistProjection;
import com.hope.apiapp.dto.PharmacistUpdateRequestDto;
import com.hope.apiapp.helper.ApiResponseSuccess;
import com.hope.apiapp.model.PasswordResetToken;
import com.hope.apiapp.model.Pharmacist;
import com.hope.apiapp.service.PasswordResetTokenService;
import com.hope.apiapp.service.PharmacistService;
import com.hope.apiapp.util.CommonUtil;

@ExtendWith(MockitoExtension.class)
public class PharmacistControllerTest {

	@InjectMocks
	private PharmacistController pharmacistController;

	@Mock
	private PharmacistService pharmacistService;

	@Mock
	private PasswordResetTokenService passwordResetService;

	@Mock
	private CommonUtil commonUtil;

	@Test
	public void testSearchtPharmacists_Success() {
		// Arrange
		Pageable pageable = PageRequest.of(0, 10);
		PharmacistDto mockPharmacist = new PharmacistDto();
		List<PharmacistDto> myList = List.of(mockPharmacist);
		Page<PharmacistDto> myPage = new PageImpl<>(myList, pageable, myList.size());

		when(pharmacistService.searchPharmacists(any(Pageable.class), isNull(), isNull())).thenReturn(myPage);

		// Act
		ResponseEntity<ApiResponseSuccess<Page<PharmacistDto>>> response = pharmacistController.searchPharmacists(0, 10,
				null, null);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testSearchPharmacists_Exception() {
		when(pharmacistService.searchPharmacists(any(Pageable.class), isNull(), isNull()))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> pharmacistController.searchPharmacists(0, 10, null, null))
				.isInstanceOf(RuntimeException.class).hasMessageContaining("Unexpected error");
	}

	@Test
	public void testGgetMyProfile_Success() {
		// Arrange
		Long id = 1L;

		PharmacistProjection mockPharmacist = mock(PharmacistProjection.class);

		when(commonUtil.getCurrentUserId()).thenReturn(id);
		when(pharmacistService.getPharmacistByIdWithLimitedFields(anyLong())).thenReturn(mockPharmacist);

		// Act
		ResponseEntity<ApiResponseSuccess<PharmacistProjection>> response = pharmacistController.getMyProfile();

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
		assertThat(response.getBody().getData().getAddress2()).isNull();
	}

	@Test
	public void testGgetMyProfile_Exception() {
		// Arrange
		Long id = 1L;

		when(commonUtil.getCurrentUserId()).thenReturn(id);
		when(pharmacistService.getPharmacistByIdWithLimitedFields(id))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> pharmacistController.getMyProfile()).isInstanceOf(RuntimeException.class)
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
		Long id = 77L;
		PharmacistAddRequestDto request = new PharmacistAddRequestDto();
		Pharmacist updatedPharmacist = new Pharmacist();
		updatedPharmacist.setPharmacistId(id);

		when(pharmacistService.addPharmacist(any(PharmacistAddRequestDto.class))).thenReturn(updatedPharmacist);

		// Act
		ResponseEntity<ApiResponseSuccess<Long>> response = pharmacistController.addPharmacist(request);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
		assertThat(response.getBody().getData()).isEqualTo(id);
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
	public void testActivatePharmacist_Success() {
		// Arrange
		Long id = 77L;
		String token = "abc";
		PasswordResetToken resetToken = new PasswordResetToken();

		when(passwordResetService.validatePasswordResetToken(anyString())).thenReturn(resetToken);
		when(pharmacistService.activatePharmacist(eq(id))).thenReturn(true);

		// Act
		ResponseEntity<ApiResponseSuccess<Long>> response = pharmacistController.activatePharmacist(id, token);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
		assertThat(response.getBody().getData()).isEqualTo(id);
	}

	@Test
	public void testActivatePharmacist_TokenException() {
		// Arrange
		Long id = 1L;
		String token = "abc";

		when(passwordResetService.validatePasswordResetToken(anyString()))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> pharmacistController.activatePharmacist(id, token))
				.isInstanceOf(RuntimeException.class).hasMessageContaining("Unexpected error");
	}

	@Test
	public void testActivatePharmacist_Exception() {
		// Arrange
		Long id = 1L;
		String token = "abc";
		PasswordResetToken resetToken = new PasswordResetToken();

		when(passwordResetService.validatePasswordResetToken(anyString())).thenReturn(resetToken);
		when(pharmacistService.activatePharmacist(eq(id))).thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> pharmacistController.activatePharmacist(id, token))
				.isInstanceOf(RuntimeException.class).hasMessageContaining("Unexpected error");
	}

	@Test
	public void testupdateMyProfile_Success() {
		// Arrange
		Long id = 1L;
		PharmacistUpdateRequestDto request = new PharmacistUpdateRequestDto();
		Pharmacist updatedPharmacist = new Pharmacist();
		updatedPharmacist.setPharmacistId(id);

		when(commonUtil.getCurrentUserId()).thenReturn(id);
		when(pharmacistService.updatePharmacist(anyLong(), any(PharmacistUpdateRequestDto.class)))
				.thenReturn(updatedPharmacist);

		// Act
		ResponseEntity<ApiResponseSuccess<Long>> response = pharmacistController.updateMyProfile(request);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
		assertThat(response.getBody().getData()).isEqualTo(id);
	}

	@Test
	public void testupdateMyProfile_Exception() {
		// Arrange
		Long id = 1L;
		PharmacistUpdateRequestDto request = new PharmacistUpdateRequestDto();

		when(commonUtil.getCurrentUserId()).thenReturn(id);
		when(pharmacistService.updatePharmacist(anyLong(), any(PharmacistUpdateRequestDto.class)))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> pharmacistController.updateMyProfile(request)).isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Unexpected error");
	}

	public void testUpdatePharmacist_Success() {
		// Arrange
		Long id = 77L;
		PharmacistUpdateRequestDto request = new PharmacistUpdateRequestDto();
		Pharmacist updatedPharmacist = new Pharmacist();
		updatedPharmacist.setPharmacistId(id);

		when(pharmacistService.updatePharmacist(eq(id), any(PharmacistUpdateRequestDto.class)))
				.thenReturn(updatedPharmacist);

		// Act
		ResponseEntity<ApiResponseSuccess<Long>> response = pharmacistController.updatePharmacist(id, request);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
		assertThat(response.getBody().getData()).isEqualTo(id);
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
