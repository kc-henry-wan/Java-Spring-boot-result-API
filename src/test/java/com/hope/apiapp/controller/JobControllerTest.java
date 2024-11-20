package com.hope.apiapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.hope.apiapp.dto.JobDto;
import com.hope.apiapp.dto.JobProjection;
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
	public void testGetFilteredJobsWithLimitedFields_Success() {
		// Arrange
		Pageable pageable = PageRequest.of(0, 10);
		JobDto returnedJob = new JobDto();
		List<JobDto> myList = new ArrayList<>();
		myList.add(returnedJob);
		Page<JobDto> myPage = new PageImpl<>(myList, pageable, myList.size());

		when(jobService.findFilteredJobsWithLimitedFields(any(Pageable.class), isNull(), isNull(), isNull(), isNull(),
				isNull(), isNull(), isNull(), isNull(), isNull())).thenReturn(myPage);

		// Act
		ResponseEntity<ApiResponseSuccess<Page<JobDto>>> response = jobController.getFilteredJobsWithLimitedFields(0,
				10, null, null, null, null, null, null, null, null);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testGetFilteredJobsWithLimitedFields_Exception() {
		// Arrange
		Pageable pageable = PageRequest.of(0, 10);
		JobDto returnedJob = new JobDto();
		List<JobDto> myList = new ArrayList<>();
		myList.add(returnedJob);

		when(jobService.findFilteredJobsWithLimitedFields(any(Pageable.class), isNull(), isNull(), isNull(), isNull(),
				isNull(), isNull(), isNull(), isNull(), isNull())).thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> jobController.getFilteredJobsWithLimitedFields(0, 10, null, null, null, null, null,
				null, null, null)).isInstanceOf(RuntimeException.class).hasMessageContaining("Unexpected error");
	}

	@Test
	public void testGetMyJobs_Success() {
		// Arrange
		Pageable pageable = PageRequest.of(0, 10);
		JobDto returnedJob = new JobDto();
		List<JobDto> myList = new ArrayList<>();
		myList.add(returnedJob);
		Page<JobDto> myPage = new PageImpl<>(myList, pageable, myList.size());

		when(jobService.findFilteredJobsWithLimitedFields(any(Pageable.class), isNull(), isNull(), isNull(), isNull(),
				isNull(), isNull(), isNull(), isNull(), isNull())).thenReturn(myPage);

		// Act
		ResponseEntity<ApiResponseSuccess<Page<JobDto>>> response = jobController.getMyJobs(0, 10, null, null, null);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testGetMyJobs_Exception() {
		// Arrange
		JobDto returnedJob = new JobDto();
		List<JobDto> myList = new ArrayList<>();
		myList.add(returnedJob);

		when(jobService.findFilteredJobsWithLimitedFields(any(Pageable.class), isNull(), isNull(), isNull(), isNull(),
				isNull(), isNull(), isNull(), isNull(), isNull())).thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> jobController.getMyJobs(0, 10, null, null, null)).isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Unexpected error");
	}

	@Test
	public void testGetJobByIdWithLimitedFields_Success() {
		// Arrange
		Long id = 1L;
		JobProjection returnedJob = mock(JobProjection.class);

		when(jobService.getJobByIdWithLimitedFields(anyLong())).thenReturn(returnedJob);

		// Act
		ResponseEntity<ApiResponseSuccess<JobProjection>> response = jobController.getJobByIdWithLimitedFields(id);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
	}

	@Test
	public void testGetJobByIdWithLimitedFields_Exception() {
		// Arrange
		Long id = 1L;

		when(jobService.getJobByIdWithLimitedFields(anyLong())).thenThrow(new RuntimeException("Unexpected error"));

		// Act & Assert
		assertThatThrownBy(() -> jobController.getJobByIdWithLimitedFields(id)).isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Unexpected error");
	}

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
		Long id = 77L;
		JobUpdateRequestDto request = new JobUpdateRequestDto();
		Job updatedJob = new Job();
		updatedJob.setJobId(id);

		when(jobService.updateJobStatus(eq(id), any(JobUpdateRequestDto.class))).thenReturn(updatedJob);

		// Act
		ResponseEntity<ApiResponseSuccess<Long>> response = jobController.updateJobStatus(id, request);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
		assertThat(response.getBody().getData()).isEqualTo(id);
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
		Long id = 77L;
		JobRequestDto request = new JobRequestDto();
		Job updatedJob = new Job();
		updatedJob.setJobId(id);

		when(jobService.updateJob(eq(id), any(JobRequestDto.class))).thenReturn(updatedJob);

		// Act
		ResponseEntity<ApiResponseSuccess<Long>> response = jobController.updateJob(id, request);

		// Assert
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getApiStatus()).isEqualTo("Success");
		assertThat(response.getBody().getData()).isEqualTo(id);
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
