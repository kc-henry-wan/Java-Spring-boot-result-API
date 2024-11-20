package com.hope.apiapp.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hope.apiapp.dto.JobRequestDto;
import com.hope.apiapp.dto.JobUpdateRequestDto;
import com.hope.apiapp.exception.ResourceConflictException;
import com.hope.apiapp.exception.ResourceNotFoundException;
import com.hope.apiapp.model.Job;
import com.hope.apiapp.repository.JobRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest

public class JobServiceTest {

	@Autowired
	private JobService jobService;

	@MockBean
	private JobRepository jobRepository;

	@Test
	public void testUpdateJob_SuccessfulUpdate() {
		// Arrange
		Long id = 1L;
		LocalTime newTime = LocalTime.of(9, 0);
		BigDecimal newTotalWorkHour = BigDecimal.valueOf(19.00);
		LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);
		LocalDateTime currentTime = LocalDateTime.now();
		String actionMode = "Edit";

		Job existingJob = new Job();
		existingJob.setUpdatedAt(originalLastModifiedDate);

		JobRequestDto request = new JobRequestDto();
		request.setAction(actionMode);
		request.setJobStartTime(newTime);
		request.setTotalWorkHour(newTotalWorkHour);
		request.setUpdatedAt(originalLastModifiedDate);

		Mockito.when(jobRepository.findById(id)).thenReturn(Optional.of(existingJob));
		Mockito.when(jobRepository.save(existingJob)).thenReturn(existingJob);

		// Act
		Job result = jobService.updateJob(id, request);

		// Assert
		assertEquals(newTime, result.getJobStartTime());
		assertEquals(newTotalWorkHour, result.getTotalWorkHour());
		assertEquals(currentTime.truncatedTo(ChronoUnit.MINUTES),
				result.getUpdatedAt().truncatedTo(ChronoUnit.MINUTES));
		Mockito.verify(jobRepository).save(existingJob);
	}

	@Test
	public void testUpdateJob_RecordNotFound() {
		// Arrange
		Long id = 1L;

		JobRequestDto request = new JobRequestDto();

		Mockito.when(jobRepository.findById(id)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> jobService.updateJob(id, request)).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	public void testUpdateJob_RecordModified() {
		// Arrange
		Long id = 1L;
		LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);
		LocalDateTime updatedLastModifiedDate = LocalDateTime.of(2024, 11, 2, 10, 0);

		Job existingJob = new Job();
		existingJob.setUpdatedAt(originalLastModifiedDate);

		JobRequestDto request = new JobRequestDto();
		request.setUpdatedAt(updatedLastModifiedDate);

		Mockito.when(jobRepository.findById(id)).thenReturn(Optional.of(existingJob));

		// Act & Assert
		assertThatThrownBy(() -> jobService.updateJob(id, request)).isInstanceOf(ResourceConflictException.class);
	}

	@Test
	public void testUpdateJobStatus_SuccessfulUpdate() {
		// Arrange
		Long id = 1L;
		String newAction = "Apply";
		String newStatus = "Applied";
		LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);
		LocalDateTime currentTime = LocalDateTime.now();

		Job existingJob = new Job();
		existingJob.setUpdatedAt(originalLastModifiedDate);

		JobUpdateRequestDto request = new JobUpdateRequestDto();
		request.setAction(newAction);
		request.setUpdatedAt(originalLastModifiedDate);

		Mockito.when(jobRepository.findById(id)).thenReturn(Optional.of(existingJob));
		Mockito.when(jobRepository.save(existingJob)).thenReturn(existingJob);

		// Act
		Job result = jobService.updateJobStatus(id, request);

		// Assert
		assertEquals(newStatus, result.getStatus());
		assertEquals(currentTime.truncatedTo(ChronoUnit.MINUTES),
				result.getUpdatedAt().truncatedTo(ChronoUnit.MINUTES));
		Mockito.verify(jobRepository).save(existingJob);
	}

	@Test
	public void testUpdateJobStatus_RecordNotFound() {
		// Arrange
		Long id = 1L;

		JobUpdateRequestDto request = new JobUpdateRequestDto();

		Mockito.when(jobRepository.findById(id)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> jobService.updateJobStatus(id, request)).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	public void testUpdateJobStatus_RecordModified() {
		// Arrange
		Long id = 1L;
		LocalDateTime originalLastModifiedDate = LocalDateTime.of(2024, 11, 1, 10, 0);
		LocalDateTime updatedLastModifiedDate = LocalDateTime.of(2024, 11, 2, 10, 0);

		Job existingJob = new Job();
		existingJob.setUpdatedAt(originalLastModifiedDate);

		JobUpdateRequestDto request = new JobUpdateRequestDto();
		request.setUpdatedAt(updatedLastModifiedDate);

		Mockito.when(jobRepository.findById(id)).thenReturn(Optional.of(existingJob));

		// Act & Assert
		assertThatThrownBy(() -> jobService.updateJobStatus(id, request)).isInstanceOf(ResourceConflictException.class);
	}

}