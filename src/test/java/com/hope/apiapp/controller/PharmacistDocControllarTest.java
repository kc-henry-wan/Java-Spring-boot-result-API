package com.hope.apiapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.hope.apiapp.dto.PharmacistDocProjection;
import com.hope.apiapp.helper.ApiResponseSuccess;
import com.hope.apiapp.model.PharmacistDoc;
import com.hope.apiapp.service.PharmacistDocService;

@ExtendWith(MockitoExtension.class)
public class PharmacistDocControllarTest {

	@InjectMocks
	private PharmacistDocControllar pharmacistDocControllar;

	@Mock
	private PharmacistDocService pharmacistDocService;

	@Test
	public void testUploadImage_Success() {
		// Arrange
		Long id = 77L;
		String imageType = "Document";
		PharmacistDoc pDoc = new PharmacistDoc();
		pDoc.setImageId(id);

		when(pharmacistDocService.saveImage(isNull(), anyString(), isNull())).thenReturn(pDoc);

		// Act
		ResponseEntity<ApiResponseSuccess<Long>> response = pharmacistDocControllar.uploadImage(imageType, null);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
		assertThat(response.getBody().getData()).isEqualTo(id);
	}

	@Test
	public void testUploadImage_Exception() {
		// Arrange
		String imageType = "Document";

		when(pharmacistDocService.saveImage(isNull(), anyString(), isNull()))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> pharmacistDocControllar.uploadImage(imageType, null))
				.isInstanceOf(RuntimeException.class).hasMessageContaining("Unexpected error");
	}

//	@Test
//	public void testViewImageByUserID_Success() {
//		// Arrange
//		Long id = 77L;
//		byte[] imageBytes = null;
//		String filename = "5.png";
//		PharmacistDoc pDoc = new PharmacistDoc();
//		pDoc.setMediaType("image/PNG");
//		pDoc.setStoredFilename(filename);
//
//		when(pharmacistDocService.viewImage(eq(id), isNull())).thenReturn(imageBytes);
//		when(pharmacistDocService.findByImageId(eq(id))).thenReturn(pDoc);
//
//		// Act
//		ResponseEntity<byte[]> response = pharmacistDocControllar.viewImageByUserID(id);
//
//		// Assert
//		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
//		assertThat(response.getBody()).isNotNull();
//	}

	@Test
	public void testViewImageByUserID_Exception() {
		// Arrange
		Long id = 77L;
		byte[] imageBytes = null;
		PharmacistDoc pDoc = new PharmacistDoc();
		pDoc.setMediaType("image/PNG");

		when(pharmacistDocService.findByImageId(eq(id))).thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> pharmacistDocControllar.viewImageByUserID(id)).isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Unexpected error");
	}

	@Test
	public void testViewImage_Exception() {
		// Arrange
		Long id = 77L;
		byte[] imageBytes = null;
		PharmacistDoc pDoc = new PharmacistDoc();
		pDoc.setMediaType("image/PNG");

		when(pharmacistDocService.findByImageId(eq(id))).thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> pharmacistDocControllar.viewImage(id)).isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Unexpected error");
	}

	@Test
	public void testGetMyDoc_Success() {
		// Arrange
		Long id = 77L;
		PharmacistDocProjection pDoc = mock(PharmacistDocProjection.class);
		List<PharmacistDocProjection> myList = new ArrayList<>();
		myList.add(pDoc);

		when(pharmacistDocService.getImageIdsByPharmacistId(isNull())).thenReturn(myList);

		// Act
		ResponseEntity<ApiResponseSuccess<List<PharmacistDocProjection>>> response = pharmacistDocControllar.getMyDoc();

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testGetMyDoc_Exception() {

		when(pharmacistDocService.getImageIdsByPharmacistId(isNull()))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> pharmacistDocControllar.getMyDoc()).isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Unexpected error");
	}

	@Test
	public void testGetImagesByPharmacistId_Success() {
		// Arrange
		Long id = 77L;
		PharmacistDocProjection pDoc = mock(PharmacistDocProjection.class);
		List<PharmacistDocProjection> myList = new ArrayList<>();
		myList.add(pDoc);

		when(pharmacistDocService.getImageIdsByPharmacistId(eq(id))).thenReturn(myList);

		// Act
		ResponseEntity<ApiResponseSuccess<List<PharmacistDocProjection>>> response = pharmacistDocControllar
				.getImagesByPharmacistId(id);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testGetImagesByPharmacistId_Exception() {
		// Arrange
		Long id = 77L;

		when(pharmacistDocService.getImageIdsByPharmacistId(eq(id)))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> pharmacistDocControllar.getImagesByPharmacistId(id))
				.isInstanceOf(RuntimeException.class).hasMessageContaining("Unexpected error");
	}
}
