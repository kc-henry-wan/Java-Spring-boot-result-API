package com.hope.apiapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hope.apiapp.dto.JobDto;
import com.hope.apiapp.dto.JobProjection;
import com.hope.apiapp.dto.JobRequestDto;
import com.hope.apiapp.dto.JobUpdateRequestDto;
import com.hope.apiapp.helper.ApiResponseSuccess;
import com.hope.apiapp.model.Job;
import com.hope.apiapp.service.JobService;
import com.hope.apiapp.util.CommonUtil;

@RestController
@RequestMapping("/api")
@Validated
public class JobController {

	private static final Logger logger = LoggerFactory.getLogger(JobController.class);

	private final JobService jobService;

	private final CommonUtil commonUtil;

	@Autowired
	public JobController(JobService jobService, CommonUtil commonUtil) {
		this.jobService = jobService;
		this.commonUtil = commonUtil;
	}

	// GET /v1/job
	@GetMapping("/v1/job")
	public ResponseEntity<ApiResponseSuccess<Page<JobDto>>> getFilteredJobsWithLimitedFields(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam(required = false) String statusCode, @RequestParam(required = false) String jobIds,
			@RequestParam(required = false) String groupCode, @RequestParam(required = false) String orderBy) {

		Pageable pageable = PageRequest.of(page, size);

		Double[] coordinate = commonUtil.getUserCoordinates();

		Page<JobDto> jobs = jobService.findFilteredJobsWithLimitedFields(pageable, coordinate[0], coordinate[1],
				fromDate, toDate, statusCode, jobIds, groupCode, null, orderBy);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", jobs), HttpStatus.OK);
	}

	// GET /v1/job
	@GetMapping("/v1/myjob")
	public ResponseEntity<ApiResponseSuccess<Page<JobDto>>> getMyJobs(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String orderBy) {

		Pageable pageable = PageRequest.of(page, size);

		Double[] coordinate = commonUtil.getUserCoordinates();
		Long userId = commonUtil.getCurrentUserId();

		Page<JobDto> jobs = jobService.findFilteredJobsWithLimitedFields(pageable, coordinate[0], coordinate[1], null,
				null, null, null, null, userId, orderBy);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", jobs), HttpStatus.OK);
	}

	@GetMapping("/admin/v1/job/{id}")
	public ResponseEntity<ApiResponseSuccess<JobProjection>> getJobByIdWithLimitedFields(@PathVariable Long id) {

		JobProjection job = jobService.getJobByIdWithLimitedFields(id);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", job), HttpStatus.OK);
	}

	@PostMapping("/admin/v1/job")
	public ResponseEntity<ApiResponseSuccess<Long>> addJob(@RequestBody JobRequestDto jobRequest) {

		Job createdJob = jobService.addJob(jobRequest);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", createdJob.getJobId()), HttpStatus.CREATED);
	}

	@PutMapping("/v1/job/{id}")
	public ResponseEntity<ApiResponseSuccess<Long>> updateJobStatus(@PathVariable Long id,
			@RequestBody JobUpdateRequestDto jobRequest) {

		Job updatedJob = jobService.updateJobStatus(id, jobRequest);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", updatedJob.getJobId()), HttpStatus.OK);
	}

	@PutMapping("/admin/v1/job/{id}")
	public ResponseEntity<ApiResponseSuccess<Long>> updateJob(@PathVariable Long id,
			@RequestBody JobRequestDto jobRequest) {

		Job updatedJob = jobService.updateJob(id, jobRequest);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", updatedJob.getJobId()), HttpStatus.OK);
	}

}
