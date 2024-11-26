package com.hope.apiapp.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hope.apiapp.dto.NegotiationAcceptRequestDto;
import com.hope.apiapp.dto.NegotiationAddRequestDto;
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
	public void testUpdateNegotiation_SuccessfulUpdate_AdminAccept() {
		// Arrange
		Long id = 1L;
		BigDecimal newHourlyRate = new BigDecimal(60.0);
		BigDecimal newTotalPaid = new BigDecimal(480.0);
		LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);
		LocalDateTime currentTime = LocalDateTime.now();
		String actionMode = "AdminAccept";
		String originalJobStatus = "Open";
		String originalStatus = "New";
		String newStatus = "Admin Accepted";

		Job existingJob = new Job();
		existingJob.setStatus(originalJobStatus);
		existingJob.setUpdatedAt(originalLastModifiedDate);

		Negotiation existingNegotiation = new Negotiation();
		existingNegotiation.setUpdatedAt(originalLastModifiedDate);
		existingNegotiation.setStatus(originalStatus);

		NegotiationUpdateRequestDto request = new NegotiationUpdateRequestDto();
		request.setJobId(id);
		request.setMode(actionMode);
		request.setCounterHourlyRate(newHourlyRate);
		request.setCounterTotalPaid(newTotalPaid);
		request.setUpdatedAt(originalLastModifiedDate);
		request.setJobUpdatedAt(originalLastModifiedDate);

		Mockito.when(jobRepository.findById(id)).thenReturn(Optional.of(existingJob));
		Mockito.when(negotiationRepository.findById(id)).thenReturn(Optional.of(existingNegotiation));
		Mockito.when(negotiationRepository.save(existingNegotiation)).thenReturn(existingNegotiation);

		// Act
		Negotiation result = negotiationService.updateNegotiation(id, request);

		// Assert
		assertEquals(null, result.getCounterHourlyRate());
		assertEquals(null, result.getCounterTotalPaid());
		assertEquals(newStatus, result.getStatus());
		assertEquals(currentTime.truncatedTo(ChronoUnit.MINUTES),
				result.getUpdatedAt().truncatedTo(ChronoUnit.MINUTES));
		Mockito.verify(negotiationRepository).save(existingNegotiation);
	}

	@Test
	public void testUpdateNegotiation_SuccessfulUpdate_Edit() {
		// Arrange
		Long id = 1L;
		BigDecimal newHourlyRate = new BigDecimal(60.0);
		BigDecimal newTotalPaid = new BigDecimal(480.0);
		LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);
		LocalDateTime currentTime = LocalDateTime.now();
		String actionMode = "Edit";
		String originalStatus = "Counter Purposed";

		Job existingJob = new Job();
		Negotiation existingNegotiation = new Negotiation();
		existingNegotiation.setUpdatedAt(originalLastModifiedDate);
		existingNegotiation.setStatus(originalStatus);

		NegotiationUpdateRequestDto request = new NegotiationUpdateRequestDto();
		request.setJobId(id);
		request.setMode(actionMode);
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
		assertEquals(originalStatus, result.getStatus());
		assertEquals(currentTime.truncatedTo(ChronoUnit.MINUTES),
				result.getUpdatedAt().truncatedTo(ChronoUnit.MINUTES));
		Mockito.verify(negotiationRepository).save(existingNegotiation);
	}

	@Test
	public void testUpdateNegotiation_SuccessfulUpdate_AdminReject() {
		// Arrange
		Long id = 1L;
		BigDecimal newHourlyRate = new BigDecimal(60.0);
		BigDecimal newTotalPaid = new BigDecimal(480.0);
		LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);
		LocalDateTime currentTime = LocalDateTime.now();
		String actionMode = "AdminReject";
		String originalStatus = "New";
		String newStatus = "Admin Rejected";

		Job existingJob = new Job();
		Negotiation existingNegotiation = new Negotiation();
		existingNegotiation.setUpdatedAt(originalLastModifiedDate);
		existingNegotiation.setStatus(originalStatus);

		NegotiationUpdateRequestDto request = new NegotiationUpdateRequestDto();
		request.setJobId(id);
		request.setMode(actionMode);
		request.setCounterHourlyRate(newHourlyRate);
		request.setCounterTotalPaid(newTotalPaid);
		request.setUpdatedAt(originalLastModifiedDate);

		Mockito.when(jobRepository.findById(id)).thenReturn(Optional.of(existingJob));
		Mockito.when(negotiationRepository.findById(id)).thenReturn(Optional.of(existingNegotiation));
		Mockito.when(negotiationRepository.save(existingNegotiation)).thenReturn(existingNegotiation);

		// Act
		Negotiation result = negotiationService.updateNegotiation(id, request);

		// Assert
		assertEquals(null, result.getCounterHourlyRate());
		assertEquals(null, result.getCounterTotalPaid());
		assertEquals(newStatus, result.getStatus());
		assertEquals(currentTime.truncatedTo(ChronoUnit.MINUTES),
				result.getUpdatedAt().truncatedTo(ChronoUnit.MINUTES));
		Mockito.verify(negotiationRepository).save(existingNegotiation);
	}

	@Test
	public void testUpdateNegotiation_Successful_Counter() {
		// Arrange
		Long id = 1L;
		BigDecimal newHourlyRate = new BigDecimal(60.0);
		BigDecimal newTotalPaid = new BigDecimal(480.0);
		LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);
		LocalDateTime currentTime = LocalDateTime.now();
		String actionMode = "Counter";
		String originalStatus = "New";
		String newStatus = "Counter Purposed";

		Job existingJob = new Job();
		Negotiation existingNegotiation = new Negotiation();
		existingNegotiation.setUpdatedAt(originalLastModifiedDate);
		existingNegotiation.setStatus(originalStatus);

		NegotiationUpdateRequestDto request = new NegotiationUpdateRequestDto();
		request.setJobId(id);
		request.setMode(actionMode);
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
		assertEquals(newStatus, result.getStatus());
		assertEquals(currentTime.truncatedTo(ChronoUnit.MINUTES),
				result.getUpdatedAt().truncatedTo(ChronoUnit.MINUTES));
		Mockito.verify(negotiationRepository).save(existingNegotiation);
	}

	@Test
	public void testUpdateNegotiation_RecordNotFound() {
		// Arrange
		Long id = 1L;

		NegotiationUpdateRequestDto request = new NegotiationUpdateRequestDto();

		Mockito.when(negotiationRepository.findById(id)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> negotiationService.updateNegotiation(id, request))
				.isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	public void testUpdateNegotiation_JobRecordNotFound() {
		// Arrange
		Long id = 1L;
		LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);

		Negotiation existingNegotiation = new Negotiation();
		existingNegotiation.setUpdatedAt(originalLastModifiedDate);

		NegotiationUpdateRequestDto request = new NegotiationUpdateRequestDto();
		request.setJobId(id);
		request.setUpdatedAt(originalLastModifiedDate);

		Mockito.when(jobRepository.findById(id)).thenReturn(Optional.empty());
		Mockito.when(negotiationRepository.findById(id)).thenReturn(Optional.of(existingNegotiation));

		assertThatThrownBy(() -> negotiationService.updateNegotiation(id, request))
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

	@Test
	public void testUpdateNegotiation_JobRecordModified() {
		// Arrange
		Long id = 1L;
		LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);
		LocalDateTime updatedLastModifiedDate = LocalDateTime.of(2024, 11, 2, 10, 0);
		String actionMode = "AdminAccept";

		Job existingJob = new Job();
		existingJob.setUpdatedAt(originalLastModifiedDate);

		Negotiation existingNegotiation = new Negotiation();
		existingNegotiation.setUpdatedAt(originalLastModifiedDate);

		NegotiationUpdateRequestDto request = new NegotiationUpdateRequestDto();
		request.setJobId(id);
		request.setMode(actionMode);
		request.setUpdatedAt(originalLastModifiedDate);
		request.setJobUpdatedAt(updatedLastModifiedDate);

		Mockito.when(jobRepository.findById(id)).thenReturn(Optional.of(existingJob));
		Mockito.when(negotiationRepository.findById(id)).thenReturn(Optional.of(existingNegotiation));

		// Act & Assert
		assertThatThrownBy(() -> negotiationService.updateNegotiation(id, request))
				.isInstanceOf(ResourceConflictException.class);
	}

	@Test
	public void testAcceptNegotiation_Successful() {
		// Arrange
		Long id = 1L;
		LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);
		LocalDateTime currentTime = LocalDateTime.now();
		String actionMode = "Counter";
		String originalJobStatus = "Open";
		String originalStatus = "Counter";
		String newStatus = "Pharmacist Accepted";

		Job existingJob = new Job();
		existingJob.setUpdatedAt(originalLastModifiedDate);
		existingJob.setStatus(originalJobStatus);

		Negotiation existingNegotiation = new Negotiation();
		existingNegotiation.setUpdatedAt(originalLastModifiedDate);
		existingNegotiation.setStatus(originalStatus);

		NegotiationAcceptRequestDto request = new NegotiationAcceptRequestDto();
		request.setJobId(id);
		request.setMode(actionMode);
		request.setUpdatedAt(originalLastModifiedDate);
		request.setJobUpdatedAt(originalLastModifiedDate);

		Mockito.when(jobRepository.findById(id)).thenReturn(Optional.of(existingJob));
		Mockito.when(negotiationRepository.findById(id)).thenReturn(Optional.of(existingNegotiation));
		Mockito.when(negotiationRepository.save(existingNegotiation)).thenReturn(existingNegotiation);

		// Act
		Negotiation result = negotiationService.acceptNegotiation(id, request);

		// Assert
		assertEquals(newStatus, result.getStatus());
		assertEquals(currentTime.truncatedTo(ChronoUnit.MINUTES),
				result.getUpdatedAt().truncatedTo(ChronoUnit.MINUTES));
		Mockito.verify(negotiationRepository).save(existingNegotiation);
	}

	@Test
	public void testAcceptNegotiation_RecordNotFound() {
		// Arrange
		Long id = 1L;

		NegotiationAcceptRequestDto request = new NegotiationAcceptRequestDto();

		Mockito.when(negotiationRepository.findById(id)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> negotiationService.acceptNegotiation(id, request))
				.isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	public void testAcceptNegotiation_JobRecordNotFound() {
		// Arrange
		Long id = 1L;
		LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);

		Negotiation existingNegotiation = new Negotiation();
		existingNegotiation.setUpdatedAt(originalLastModifiedDate);

		NegotiationAcceptRequestDto request = new NegotiationAcceptRequestDto();
		request.setJobId(id);
		request.setUpdatedAt(originalLastModifiedDate);

		Mockito.when(jobRepository.findById(id)).thenReturn(Optional.empty());
		Mockito.when(negotiationRepository.findById(id)).thenReturn(Optional.of(existingNegotiation));

		assertThatThrownBy(() -> negotiationService.acceptNegotiation(id, request))
				.isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	public void testAcceptNegotiation_RecordModified() {
		// Arrange
		Long id = 1L;
		LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);
		LocalDateTime updatedLastModifiedDate = LocalDateTime.of(2024, 11, 2, 10, 0);

		Negotiation existingNegotiation = new Negotiation();
		existingNegotiation.setUpdatedAt(originalLastModifiedDate);

		NegotiationAcceptRequestDto request = new NegotiationAcceptRequestDto();
		request.setUpdatedAt(updatedLastModifiedDate);

		Mockito.when(negotiationRepository.findById(id)).thenReturn(Optional.of(existingNegotiation));

		// Act & Assert
		assertThatThrownBy(() -> negotiationService.acceptNegotiation(id, request))
				.isInstanceOf(ResourceConflictException.class);
	}

	@Test
	public void testAcceptNegotiation_JobStatusModified() {
		// Arrange
		Long id = 1L;
		String status = "Assigned";
		LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);

		Job existingJob = new Job();
		existingJob.setStatus(status);
		existingJob.setUpdatedAt(originalLastModifiedDate);

		Negotiation existingNegotiation = new Negotiation();
		existingNegotiation.setUpdatedAt(originalLastModifiedDate);

		NegotiationAcceptRequestDto request = new NegotiationAcceptRequestDto();
		request.setJobId(id);
		request.setUpdatedAt(originalLastModifiedDate);
		request.setJobUpdatedAt(originalLastModifiedDate);

		Mockito.when(jobRepository.findById(id)).thenReturn(Optional.of(existingJob));
		Mockito.when(negotiationRepository.findById(id)).thenReturn(Optional.of(existingNegotiation));

		// Act & Assert
		assertThatThrownBy(() -> negotiationService.acceptNegotiation(id, request))
				.isInstanceOf(ResourceConflictException.class);
	}

	@Test
	public void testAcceptNegotiation_JobRecordModified() {
		// Arrange
		Long id = 1L;
		LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);
		LocalDateTime updatedLastModifiedDate = LocalDateTime.of(2024, 11, 2, 10, 0);
		String actionMode = "AdminAccept";

		Job existingJob = new Job();
		existingJob.setUpdatedAt(originalLastModifiedDate);

		Negotiation existingNegotiation = new Negotiation();
		existingNegotiation.setUpdatedAt(originalLastModifiedDate);

		NegotiationAcceptRequestDto request = new NegotiationAcceptRequestDto();
		request.setJobId(id);
		request.setMode(actionMode);
		request.setUpdatedAt(originalLastModifiedDate);
		request.setJobUpdatedAt(updatedLastModifiedDate);

		Mockito.when(jobRepository.findById(id)).thenReturn(Optional.of(existingJob));
		Mockito.when(negotiationRepository.findById(id)).thenReturn(Optional.of(existingNegotiation));

		// Act & Assert
		assertThatThrownBy(() -> negotiationService.acceptNegotiation(id, request))
				.isInstanceOf(ResourceConflictException.class);
	}

	@Test
	public void testAddNegotiation_JobRecordNotFound() {
		// Arrange
		Long id = 1L;

		Negotiation existingNegotiation = new Negotiation();

		NegotiationAddRequestDto request = new NegotiationAddRequestDto();
		request.setJobId(id);

		Mockito.when(jobRepository.findById(id)).thenReturn(Optional.empty());
		Mockito.when(negotiationRepository.findById(id)).thenReturn(Optional.of(existingNegotiation));

		assertThatThrownBy(() -> negotiationService.addNegotiation(request))
				.isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	public void testAddNegotiation_JobStatusModified() {
		// Arrange
		Long id = 1L;
		String status = "Assigned";
		LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);

		Job existingJob = new Job();
		existingJob.setStatus(status);
		existingJob.setUpdatedAt(originalLastModifiedDate);

		NegotiationAddRequestDto request = new NegotiationAddRequestDto();
		request.setJobId(id);

		Mockito.when(jobRepository.findById(id)).thenReturn(Optional.of(existingJob));

		// Act & Assert
		assertThatThrownBy(() -> negotiationService.addNegotiation(request))
				.isInstanceOf(ResourceConflictException.class);
	}

}