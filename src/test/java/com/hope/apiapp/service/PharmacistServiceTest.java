package com.hope.apiapp.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hope.apiapp.dto.PharmacistUpdateRequestDto;
import com.hope.apiapp.exception.ResourceNotFoundException;
import com.hope.apiapp.model.Pharmacist;
import com.hope.apiapp.repository.PharmacistRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest

public class PharmacistServiceTest {

	@Autowired
	private PharmacistService pharmacistService;

	@MockBean
	private PharmacistRepository pharmacistRepository;

	@Test
	public void testUpdatePharmacist_SuccessfulUpdate() {
		// Arrange
		Long id = 1L;
		String newLastName = "David";
		String newMobile = "986532741741";
//		LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);

		Pharmacist existingPharmacist = new Pharmacist();

		PharmacistUpdateRequestDto request = new PharmacistUpdateRequestDto();
		request.setLastName(newLastName);
		request.setMobile(newMobile);

		Mockito.when(pharmacistRepository.findById(id)).thenReturn(Optional.of(existingPharmacist));
		Mockito.when(pharmacistRepository.save(existingPharmacist)).thenReturn(existingPharmacist);

		// Act
		Pharmacist result = pharmacistService.updatePharmacist(id, request);

		// Assert
		assertEquals(newLastName, result.getLastName());
		assertEquals(newMobile, result.getMobile());
		Mockito.verify(pharmacistRepository).save(existingPharmacist);
	}

	@Test
	public void testUpdatePharmacist_RecordNotFound() {
		// Arrange
		Long id = 1L;

		PharmacistUpdateRequestDto pharmacistRequest = new PharmacistUpdateRequestDto();

		Mockito.when(pharmacistRepository.findById(id)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> pharmacistService.updatePharmacist(id, pharmacistRequest))
				.isInstanceOf(ResourceNotFoundException.class);
	}

//	@Test
//	public void testUpdatePharmacist_RecordModified() {
//		// Arrange
//		Long id = 1L;
//		Double newHourlyRate = 60.0;
//		LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);
//		LocalDateTime updatedLastModifiedDate = LocalDateTime.of(2024, 11, 2, 10, 0);
//
//		Pharmacist existingPharmacist = new Pharmacist();
//
//		Mockito.when(pharmacistRepository.findById(id)).thenReturn(Optional.of(existingPharmacist));
//
//		// Act
//		//TODO:Check last modificationDate
////		Pharmacist result = pharmacistService.updatePharmacist(id, existingPharmacist, originalLastModifiedDate);
//
//		// Assert
//		if (result != null) {
//		}
//		Mockito.verify(pharmacistRepository, Mockito.never()).save(existingPharmacist);
//	}

}