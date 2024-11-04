package com.hope.apiapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.hope.apiapp.dto.JobRequestDto;
import com.hope.apiapp.dto.JobUpdateRequestDto;
import com.hope.apiapp.helper.ApiResponseSuccess;
import com.hope.apiapp.model.Job;
import com.hope.apiapp.service.JobService;

@ExtendWith(MockitoExtension.class)
public class JobControllerTest {

	@InjectMocks
	private JobController jobController;

	@Mock
	private JobService jobService;

	@Test
	public void testAddJob_Success() {
		// Arrange
		JobRequestDto request = new JobRequestDto();
		Job updatedJob = new Job();

		when(jobService.addJob(any(JobRequestDto.class))).thenReturn(updatedJob);

		// Act
		ResponseEntity<ApiResponseSuccess<Long>> response = jobController.addJob(request);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testAddJob_Exception() {
		// Arrange
		JobRequestDto request = new JobRequestDto();

		when(jobService.addJob(any(JobRequestDto.class))).thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> jobController.addJob(request)).isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Unexpected error");
	}

	@Test
	public void testUpdateJobStatus_Success() {
		// Arrange
		Long id = 1L;
		JobUpdateRequestDto request = new JobUpdateRequestDto();
		Job updatedJob = new Job();

		when(jobService.updateJobStatus(eq(id), any(JobUpdateRequestDto.class))).thenReturn(updatedJob);

		// Act
		ResponseEntity<ApiResponseSuccess<Long>> response = jobController.updateJobStatus(id, request);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testUpdateJobStatus_Exception() {
		// Arrange
		Long id = 1L;
		JobUpdateRequestDto request = new JobUpdateRequestDto();

		when(jobService.updateJobStatus(eq(id), any(JobUpdateRequestDto.class)))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> jobController.updateJobStatus(id, request)).isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Unexpected error");
	}

	@Test
	public void testUpdateJob_Success() {
		// Arrange
		Long id = 1L;
		JobRequestDto request = new JobRequestDto();
		Job updatedJob = new Job();

		when(jobService.updateJob(eq(id), any(JobRequestDto.class))).thenReturn(updatedJob);

		// Act
		ResponseEntity<ApiResponseSuccess<Long>> response = jobController.updateJob(id, request);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testUpdateJob_Exception() {
		// Arrange
		Long id = 1L;
		JobRequestDto request = new JobRequestDto();

		when(jobService.updateJob(eq(id), any(JobRequestDto.class)))
				.thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> jobController.updateJob(id, request)).isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Unexpected error");
	}

}
