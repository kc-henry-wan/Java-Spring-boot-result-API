package com.hope.apiapp.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hope.apiapp.dto.NegotiationUpdateRequestDto;
import com.hope.apiapp.exception.ResourceConflictException;
import com.hope.apiapp.exception.ResourceNotFoundException;
import com.hope.apiapp.model.Job;
import com.hope.apiapp.model.Negotiation;
import com.hope.apiapp.repository.JobRepository;
import com.hope.apiapp.repository.NegotiationRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest

public class NegotiationServiceTest {

	@Autowired
	private NegotiationService negotiationService;

	@MockBean
	private NegotiationRepository negotiationRepository;

	@MockBean
	private JobRepository jobRepository;

	@Test
	public void testUpdateNegotiation_SuccessfulUpdate() {
		// Arrange
		Long id = 1L;
		BigDecimal newHourlyRate = new BigDecimal(60.0);
		BigDecimal newTotalPaid = new BigDecimal(480.0);
		LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);

		Job existingJob = new Job();
		Negotiation existingNegotiation = new Negotiation();
		existingNegotiation.setUpdatedAt(originalLastModifiedDate);

		NegotiationUpdateRequestDto request = new NegotiationUpdateRequestDto();
		request.setJobId(id);
		request.setCounterHourlyRate(newHourlyRate);
		request.setCounterTotalPaid(newTotalPaid);
		request.setUpdatedAt(originalLastModifiedDate);

		Mockito.when(jobRepository.findById(id)).thenReturn(Optional.of(existingJob));
		Mockito.when(negotiationRepository.findById(id)).thenReturn(Optional.of(existingNegotiation));
		Mockito.when(negotiationRepository.save(existingNegotiation)).thenReturn(existingNegotiation);

		// Act
		Negotiation result = negotiationService.updateNegotiation(id, request);

		// Assert
		assertEquals(newHourlyRate, result.getCounterHourlyRate());
		assertEquals(newTotalPaid, result.getCounterTotalPaid());
		Mockito.verify(negotiationRepository).save(existingNegotiation);
	}

	@Test
	public void testUpdateNegotiation_RecordNotFound() {
		// Arrange
		Long id = 1L;

		NegotiationUpdateRequestDto negotiationRequest = new NegotiationUpdateRequestDto();

		Mockito.when(negotiationRepository.findById(id)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> negotiationService.updateNegotiation(id, negotiationRequest))
				.isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	public void testUpdateNegotiation_RecordModified() {
		// Arrange
		Long id = 1L;
		LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);
		LocalDateTime updatedLastModifiedDate = LocalDateTime.of(2024, 11, 2, 10, 0);

		Negotiation existingNegotiation = new Negotiation();
		existingNegotiation.setUpdatedAt(originalLastModifiedDate);

		NegotiationUpdateRequestDto request = new NegotiationUpdateRequestDto();
		request.setUpdatedAt(updatedLastModifiedDate);

		Mockito.when(negotiationRepository.findById(id)).thenReturn(Optional.of(existingNegotiation));

		// Act & Assert
		assertThatThrownBy(() -> negotiationService.updateNegotiation(id, request))
				.isInstanceOf(ResourceConflictException.class);
	}

}