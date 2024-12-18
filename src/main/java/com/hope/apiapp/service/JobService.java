package com.hope.apiapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hope.apiapp.dto.JobDto;
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

	@Autowired
	private CommonUtil commonUtil;

	@Transactional
	public Page<JobDto> findFilteredJobsWithLimitedFields(Pageable pageable, Double fromLat, Double fromLng,
			String fromDate, String toDate, String statusCode, String jobIds, String groupCode, Long pharmacistId,
			String orderBy) {
		logger.info("JobService - findFilteredJobsWithLimitedFields");
		logger.info("fromLat=" + fromLat);
		logger.info("fromLng=" + fromLng);
		logger.info("fromDate=" + fromDate);
		logger.info("toDate=" + toDate);
		logger.info("statusCode=" + statusCode);
		logger.info("jobIds=" + jobIds);
		logger.info("groupCode=" + groupCode);
		logger.info("pharmacistId=" + pharmacistId);
		logger.info("orderBy=" + orderBy);

		return jobRepository.findFilteredJobsWithLimitedFields(pageable, fromLat, fromLng, fromDate, toDate, statusCode,
				jobIds, groupCode, pharmacistId, orderBy);
	}

	public JobProjection getJobByIdWithLimitedFields(Long id) {
		return jobRepository.getJobByIdWithLimitedFields(id)
				.orElseThrow(() -> new ResourceNotFoundException("RNF-J001-" + id));
	}

	public Job findById(Long id) {
		return jobRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("RNF-J002-" + id));
	}

	public Job addJob(JobRequestDto jobRequest) {

		Job job = new Job();

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

		job.setStatus("Open"); // Default status
		job.setUpdatedUserId(commonUtil.getCurrentUserId()); // Retrieve from the current session
		job.setCreatedAt(LocalDateTime.now()); // Set current time for creation
		job.setUpdatedAt(LocalDateTime.now()); // Set current time for update

		Job createdJob = jobRepository.save(job);
		String RefNo = generateRef(createdJob.getJobId());
		createdJob.setJobRef(RefNo);

		return jobRepository.save(createdJob);
	}

	public Job updateJobStatus(Long id, JobUpdateRequestDto jobRequest) {

		Job job = findById(id);

		if (job != null) {
			if (!jobRequest.getUpdatedAt().equals(job.getUpdatedAt())) {
				throw new ResourceConflictException("RCE-J401");
			}
			if ("Open".equalsIgnoreCase(job.getStatus())) {

				job.setUpdatedAt(LocalDateTime.now()); // Set current time for update
				job.setUpdatedUserId(commonUtil.getCurrentUserId()); // Retrieve from the current session

				if ("Apply".equalsIgnoreCase(jobRequest.getAction())) {
					job.setStatus("Applied");
					job.setPharmacistId(commonUtil.getCurrentUserId());

					// Check if any open negotiation
					List<Negotiation> negotiationList = negotiationRepository.findByJobId(id);

					if (negotiationList.size() > 0) {

						for (Negotiation negotiation : negotiationList) {
							negotiation.setStatus("Job Picked by others");
							negotiation.setUpdatedAt(LocalDateTime.now());
							negotiation.setUpdatedUserId(commonUtil.getCurrentUserId());
						}

						return updateWithTrxHandling(job, negotiationList);
					}

				} else if ("Withdraw".equalsIgnoreCase(jobRequest.getAction())) {
					job.setStatus("Open");
					job.setPharmacistId(null);
				}

				return jobRepository.save(job);
			} else {
				throw new ResourceConflictException("RCE-J901");
			}
		} else {
			throw new ResourceNotFoundException("RNF-J003-" + id);
		}
	}

	public Job updateJob(Long id, JobRequestDto jobRequest) {

		Job job = findById(id);

		if (job != null) {
			if (!jobRequest.getUpdatedAt().equals(job.getUpdatedAt())) {
				throw new ResourceConflictException("RCE-J402");
			}

			if ("Edit".equalsIgnoreCase(jobRequest.getAction())) {
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
			} else if ("Delete".equalsIgnoreCase(jobRequest.getAction())) {
				job.setDeleted(true);
			}

			job.setUpdatedAt(LocalDateTime.now()); // Set current time for update
			job.setUpdatedUserId(commonUtil.getCurrentUserId()); // Retrieve from the current session

			return jobRepository.save(job);
		} else {
			throw new ResourceNotFoundException("RNF-J004-" + id);

		}
	}

	@Transactional
	public Job updateWithTrxHandling(Job job, List<Negotiation> negotiationList) {
		Job updatedJob = jobRepository.save(job);

		if (negotiationList.size() > 0) {
			for (Negotiation negotiation : negotiationList) {
				negotiationRepository.save(negotiation);
			}
		}

		return updatedJob;
	}

	public static String generateRef(Long x) {
		if (x > 100000) {
			return "REF" + x;
		} else {
			return String.format("REF%05d", x);
		}
	}
}
