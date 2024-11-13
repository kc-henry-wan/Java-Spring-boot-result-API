package com.hope.apiapp.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hope.apiapp.dto.JobDTO;
import com.hope.apiapp.dto.JobProjection;
import com.hope.apiapp.dto.JobRequestDto;
import com.hope.apiapp.dto.JobUpdateRequestDto;
import com.hope.apiapp.exception.ResourceConflictException;
import com.hope.apiapp.exception.ResourceNotFoundException;
import com.hope.apiapp.model.Job;
import com.hope.apiapp.model.Negotiation;
import com.hope.apiapp.repository.JobRepository;
import com.hope.apiapp.repository.NegotiationRepository;
import com.hope.apiapp.util.CommonUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class JobService {

	private static final Logger logger = LoggerFactory.getLogger(JobService.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private NegotiationRepository negotiationRepository;

	@Transactional
	public Page<JobDTO> findFilteredJobsWithLimitedFields(Pageable pageable, Double fromLat, Double fromLng,
			String fromDate, String toDate, String statusCode, String jobIds, String groupCode, Long pharmacistId) {
		logger.info("JobService - findFilteredJobsWithLimitedFields");

		return jobRepository.findFilteredJobsWithLimitedFields(pageable, fromLat, fromLng, fromDate, toDate, statusCode,
				jobIds, groupCode, pharmacistId);
	}

	public JobProjection getJobByIdWithLimitedFields(Long id) {
		logger.info("findById: " + id);
		return jobRepository.getJobByIdWithLimitedFields(id)
				.orElseThrow(() -> new ResourceNotFoundException("Job not found"));
	}

	public Job findById(Long id) {
		logger.info("findById: " + id);
		return jobRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Job not found"));
	}

	public Job addJob(JobRequestDto jobRequest) {
		logger.info("addJob");

		Job job = new Job();

//TODO:add logic to gen ref number
//		job.setJobRef();
		job.setDescription(jobRequest.getDescription());
		job.setPharmacyGroupId(jobRequest.getPharmacyGroupId());
		job.setPharmacyBranchId(jobRequest.getPharmacyBranchId());
		job.setJobDate(jobRequest.getJobDate());
		job.setJobStartTime(jobRequest.getJobStartTime());
		job.setJobEndTime(jobRequest.getJobEndTime());
		job.setHourlyRate(jobRequest.getHourlyRate());
		job.setTotalWorkHour(jobRequest.getTotalWorkHour());
		job.setTotalPaid(jobRequest.getTotalPaid());
		job.setLunchArrangement(jobRequest.getLunchArrangement());
		job.setParkingOption(jobRequest.getParkingOption());
		job.setRatePerMile(jobRequest.getRatePerMile());

		// Set default values for additional fields
		job.setStatus("Active"); // Default status
		job.setUpdatedUserId(CommonUtil.getCurrentUserId()); // Retrieve from the current session
		job.setCreatedAt(LocalDateTime.now()); // Set current time for creation
		job.setUpdatedAt(LocalDateTime.now()); // Set current time for update

		return jobRepository.save(job);
	}

	public Job updateJobStatus(Long id, JobUpdateRequestDto jobRequest) {

		logger.info("updateJobStatus: " + id);
		Job job = findById(id);

		if (job != null) {
			logger.info("job is not null");

			if (!jobRequest.getUpdatedAt().equals(job.getUpdatedAt())) {
				throw new ResourceConflictException("Record has been modified by another user.");
			}

			job.setStatus(jobRequest.getStatus());
			job.setUpdatedAt(LocalDateTime.now()); // Set current time for update
			job.setUpdatedUserId(CommonUtil.getCurrentUserId()); // Retrieve from the current session

			if ("Apply".equalsIgnoreCase(jobRequest.getStatus())) {
				job.setPharmacistId(CommonUtil.getCurrentUserId());

				// TODO: Check if any open negotiation
				Negotiation negotiation = negotiationRepository.findByJobId(id).orElseThrow();
				if (negotiation != null) {
					negotiation.setStatus("Job Picked by others");
					negotiation.setUpdatedAt(LocalDateTime.now()); // Set current time for update
					negotiation.setUpdatedUserId(CommonUtil.getCurrentUserId()); // Retrieve from the current session
				}

				return updateWithTrxHandling(job, negotiation);

			} else if ("Withdraw".equalsIgnoreCase(jobRequest.getStatus())) {
				job.setPharmacistId(null);

				return jobRepository.save(job);
			} else {
				return jobRepository.save(job);
			}

		} else {
			logger.info("job is null");

			throw new ResourceNotFoundException("Job not found with ID " + id);

		}
	}

	public Job updateJob(Long id, JobRequestDto jobRequest) {

		logger.info("updateJob: " + id);
		Job job = findById(id);

		if (job != null) {
			logger.info("job is not null");

			if (!jobRequest.getUpdatedAt().equals(job.getUpdatedAt())) {
				throw new ResourceConflictException("Record has been modified by another user.");
			}

			job.setDescription(jobRequest.getDescription());
			job.setPharmacyGroupId(jobRequest.getPharmacyGroupId());
			job.setPharmacyBranchId(jobRequest.getPharmacyBranchId());
			job.setJobDate(jobRequest.getJobDate());
			job.setJobStartTime(jobRequest.getJobStartTime());
			job.setJobEndTime(jobRequest.getJobEndTime());
			job.setHourlyRate(jobRequest.getHourlyRate());
			job.setTotalWorkHour(jobRequest.getTotalWorkHour());
			job.setTotalPaid(jobRequest.getTotalPaid());
			job.setLunchArrangement(jobRequest.getLunchArrangement());
			job.setParkingOption(jobRequest.getParkingOption());
			job.setRatePerMile(jobRequest.getRatePerMile());

			job.setUpdatedAt(LocalDateTime.now()); // Set current time for update
			job.setUpdatedUserId(CommonUtil.getCurrentUserId()); // Retrieve from the current session

			return jobRepository.save(job);
		} else {
			logger.info("job is null");

			throw new ResourceNotFoundException("Job not found with ID " + id);

		}
	}

	@Transactional
	public Job updateWithTrxHandling(Job job, Negotiation negotiation) {
		Job updatedJob = jobRepository.save(job);

		if (negotiation != null) {
			Negotiation updatedNegotiation = negotiationRepository.save(negotiation);
		}

		return updatedJob;
	}

}
