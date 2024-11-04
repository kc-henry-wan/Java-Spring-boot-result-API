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

import com.hope.apiapp.dto.PharmacyBranchRequestDto;
import com.hope.apiapp.exception.ResourceNotFoundException;
import com.hope.apiapp.model.PharmacyBranch;
import com.hope.apiapp.repository.PharmacyBranchRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest

public class PharmacyBranchServiceTest {

	@Autowired
	private PharmacyBranchService pharmacyBranchService;

	@MockBean
	private PharmacyBranchRepository pharmacyBranchRepository;

	@Test
	public void testUpdatePharmacyBranch_SuccessfulUpdate() {
		// Arrange
		Long id = 1L;
		String newBranchName = "Richmond Branch";
		String newAddress = "45 Park Road";
//		LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);

		PharmacyBranch existingPharmacyBranch = new PharmacyBranch();

		PharmacyBranchRequestDto request = new PharmacyBranchRequestDto();
		request.setBranchName(newBranchName);
		request.setAddress1(newAddress);

		Mockito.when(pharmacyBranchRepository.findById(id)).thenReturn(Optional.of(existingPharmacyBranch));
		Mockito.when(pharmacyBranchRepository.save(existingPharmacyBranch)).thenReturn(existingPharmacyBranch);

		// Act
		PharmacyBranch result = pharmacyBranchService.updatePharmacyBranch(id, request);

		// Assert
		assertEquals(newBranchName, result.getBranchName());
		assertEquals(newAddress, result.getAddress1());
		Mockito.verify(pharmacyBranchRepository).save(existingPharmacyBranch);
	}

	@Test
	public void testUpdatePharmacyBranch_RecordNotFound() {
		// Arrange
		Long id = 1L;

		PharmacyBranchRequestDto pharmacyBranchRequest = new PharmacyBranchRequestDto();

		Mockito.when(pharmacyBranchRepository.findById(id)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> pharmacyBranchService.updatePharmacyBranch(id, pharmacyBranchRequest))
				.isInstanceOf(ResourceNotFoundException.class);
	}

//	@Test
//	public void testUpdatePharmacyBranch_RecordModified() {
//		// Arrange
//		Long id = 1L;
//		Double newHourlyRate = 60.0;
//		LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);
//		LocalDateTime updatedLastModifiedDate = LocalDateTime.of(2024, 11, 2, 10, 0);
//
//		PharmacyBranch existingPharmacyBranch = new PharmacyBranch();
//
//		Mockito.when(pharmacyBranchRepository.findById(id)).thenReturn(Optional.of(existingPharmacyBranch));
//
//		// Act
//		//TODO:Check last modificationDate
////		PharmacyBranch result = pharmacyBranchService.updatePharmacyBranch(id, existingPharmacyBranch, originalLastModifiedDate);
//
//		// Assert
//		if (result != null) {
//		}
//		Mockito.verify(pharmacyBranchRepository, Mockito.never()).save(existingPharmacyBranch);
//	}

}