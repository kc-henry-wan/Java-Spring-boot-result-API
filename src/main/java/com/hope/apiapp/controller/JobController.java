package com.hope.apiapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.hope.apiapp.dto.JobDTO;
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
//@CrossOrigin(origins = "http://localhost:73")

public class JobController {

	private static final Logger logger = LoggerFactory.getLogger(JobController.class);

	private final JobService jobService;

	@Autowired
	public JobController(JobService jobService) {
		this.jobService = jobService;
	}

	// GET /v1/job
	@GetMapping("/v1/job")
	public ResponseEntity<ApiResponseSuccess<List<JobDTO>>> getFilteredJobsWithLimitedFields(
			@RequestParam(required = false) Double fromLat, @RequestParam(required = false) Double fromLng,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam(required = false) String statusCode, @RequestParam(required = false) String jobIds,
			@RequestParam(required = false) String groupCode, @RequestParam(required = false) String sortingSeq) {

		logger.info("JobController - getFilteredJobsWithLimitedFields start");

		List<JobDTO> jobs = jobService.findFilteredJobsWithLimitedFields(fromLat, fromLng, fromDate, toDate, statusCode,
				jobIds, groupCode, null, sortingSeq);

		logger.info("JobController - List<JobDTO> returned");

//		return ResponseEntity.ok(jobs);
		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", jobs), HttpStatus.OK);
	}

	// GET /v1/job
	@GetMapping("/v1/myjob")
	public ResponseEntity<ApiResponseSuccess<List<JobDTO>>> getMyJobs(@RequestParam(required = false) Double fromLat,
			@RequestParam(required = false) Double fromLng, @RequestParam(required = false) String groupCode,
			@RequestParam(required = false) String sortingSeq) {

		logger.info("JobController - getMyJobs start");

		List<JobDTO> jobs = jobService.findFilteredJobsWithLimitedFields(fromLat, fromLng, null, null, null, null, null,
				CommonUtil.getCurrentUserId(), sortingSeq);

		logger.info("JobController - List<JobDTO> returned");

//		return ResponseEntity.ok(jobs);
		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", jobs), HttpStatus.OK);
	}

	@GetMapping("/v1/staff/job/{id}")
	public ResponseEntity<ApiResponseSuccess<JobProjection>> getJobById(@PathVariable Long id) {
		logger.info("getJobById");

		JobProjection job = jobService.getJobByIdWithLimitedFields(id);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", job), HttpStatus.OK);
	}

	@PostMapping("/v1/job")
	public ResponseEntity<ApiResponseSuccess<Long>> addJob(@RequestBody JobRequestDto jobRequest) {
		logger.info("addJob");

		Job createdJob = jobService.addJob(jobRequest);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", createdJob.getJobId()), HttpStatus.CREATED);
	}

	@PutMapping("/v1/job/{id}")
	public ResponseEntity<ApiResponseSuccess<Long>> updateJobStatus(@PathVariable Long id,
			@RequestBody JobUpdateRequestDto jobRequest) {
		logger.info("updateJob");

		Job updatedJob = jobService.updateJobStatus(id, jobRequest);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", updatedJob.getJobId()), HttpStatus.OK);
	}

	@PutMapping("/staff/v1/job/{id}")
	public ResponseEntity<ApiResponseSuccess<Long>> updateJob(@PathVariable Long id,
			@RequestBody JobRequestDto jobRequest) {
		logger.info("updateJob");

		Job updatedJob = jobService.updateJob(id, jobRequest);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", updatedJob.getJobId()), HttpStatus.OK);
	}

}
