package com.hope.apiapp.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hope.apiapp.dto.NegotiationAcceptRequestDto;
import com.hope.apiapp.dto.NegotiationAddRequestDto;
import com.hope.apiapp.dto.NegotiationDto;
import com.hope.apiapp.dto.NegotiationProjection;
import com.hope.apiapp.dto.NegotiationUpdateRequestDto;
import com.hope.apiapp.exception.ResourceConflictException;
import com.hope.apiapp.exception.ResourceNotFoundException;
import com.hope.apiapp.model.Job;
import com.hope.apiapp.model.Negotiation;
import com.hope.apiapp.repository.JobRepository;
import com.hope.apiapp.repository.NegotiationRepository;
import com.hope.apiapp.util.CommonUtil;

@Service
public class NegotiationService {

	private static final Logger logger = LoggerFactory.getLogger(NegotiationService.class);

	@Autowired
	private NegotiationRepository negotiationRepository;

	@Autowired
	private JobRepository jobRepository;

	public Page<NegotiationDto> getNegotiations(Pageable pageable, String status, Long pharmacistId) {
		logger.info("NegotiationService - findByStatus: " + status);
		return negotiationRepository.getNegotiations(pageable, status, pharmacistId);

	}

	public NegotiationProjection getNegotiationById(Long id) {
		logger.info("findNegotiationById: " + id);
		return negotiationRepository.getNegotiationById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Negotiation not found"));
	}

	public Negotiation findById(Long id) {
		logger.info("findNegotiationById: " + id);
		return negotiationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Negotiation not found"));
	}

	public Negotiation addNegotiation(NegotiationAddRequestDto negotiationRequest) {
		logger.info("addNegotiation");

		Negotiation negotiation = new Negotiation();

		// Set default values for additional fields
		negotiation.setStatus("New"); // Default status
		negotiation.setUpdatedUserId(CommonUtil.getCurrentUserId()); // Retrieve from the current session
		negotiation.setCreatedAt(LocalDateTime.now()); // Set current time for creation
		negotiation.setUpdatedAt(LocalDateTime.now()); // Set current time for update

		return negotiationRepository.save(negotiation);
	}

	public Negotiation updateNegotiation(Long id, NegotiationUpdateRequestDto negotiationRequest) {

		logger.info("updateNegotiation: " + id);
		Long jobId = negotiationRequest.getJobId();

		Negotiation negotiation = findById(id);
		Job job = jobRepository.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job not found"));

		if (negotiation != null && job != null) {
			logger.info("negotiation is not null");

			if (!negotiationRequest.getUpdatedAt().equals(negotiation.getUpdatedAt())) {
				throw new ResourceConflictException("Record has been modified by another user.");
			}

			negotiation.setUpdatedAt(LocalDateTime.now()); // Set current time for update
			negotiation.setUpdatedUserId(CommonUtil.getCurrentUserId()); // Retrieve from the current session

			if ("AdminAccept".equalsIgnoreCase(negotiationRequest.getMode())) {
				negotiation.setStatus("Admin Accepted");
			} else if (("Counter".equalsIgnoreCase(negotiationRequest.getMode()))
					|| ("Edit".equalsIgnoreCase(negotiationRequest.getMode()))) {
				negotiation.setStatus("Counter Purposed");
				negotiation.setCounterHourlyRate(negotiationRequest.getCounterHourlyRate());
				negotiation.setCounterTotalPaid(negotiationRequest.getCounterTotalPaid());
			} else if ("AdminReject".equalsIgnoreCase(negotiationRequest.getMode())) {
				negotiation.setStatus("Admin Rejected");
			}

			return negotiationRepository.save(negotiation);

		} else {
			logger.info("negotiation is null");

			throw new ResourceNotFoundException("Negotiation not found with ID " + id);

		}
	}

	public Negotiation acceptNegotiation(Long id, NegotiationAcceptRequestDto negotiationRequest) {

		logger.info("acceptNegotiation: " + id);
		Long jobId = negotiationRequest.getJobId();

		Negotiation negotiation = findById(id);
		Job job = jobRepository.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job not found"));

		if (negotiation != null && job != null) {
			logger.info("negotiation is not null");

			if (!negotiationRequest.getUpdatedAt().equals(negotiation.getUpdatedAt())) {
				throw new ResourceConflictException("Record has been modified by another user.");
			}

			negotiation.setUpdatedAt(LocalDateTime.now()); // Set current time for update
			negotiation.setUpdatedUserId(CommonUtil.getCurrentUserId()); // Retrieve from the current session
			negotiation.setStatus("Pharmacist Accepted");

			job.setPharmacistId(CommonUtil.getCurrentUserId());
			job.setStatus("Assigned");
			job.setUpdatedAt(LocalDateTime.now());
			job.setUpdatedUserId(CommonUtil.getCurrentUserId());

			return updateWithTrxHandling(job, negotiation);

		} else {
			logger.info("negotiation is null");

			throw new ResourceNotFoundException("Negotiation not found with ID " + id);

		}
	}

	@Transactional
	public Negotiation updateWithTrxHandling(Job job, Negotiation negotiation) {
		Job updatedJob = jobRepository.save(job);
		Negotiation updatedNegotiation = negotiationRepository.save(negotiation);

		return updatedNegotiation;
	}
}
