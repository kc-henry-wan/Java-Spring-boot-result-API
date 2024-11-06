package com.hope.apiapp.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hope.apiapp.dto.NegotiationAddRequestDto;
import com.hope.apiapp.dto.NegotiationUpdateRequestDto;
import com.hope.apiapp.exception.ResourceConflictException;
import com.hope.apiapp.exception.ResourceNotFoundException;
import com.hope.apiapp.model.Negotiation;
import com.hope.apiapp.repository.NegotiationRepository;
import com.hope.apiapp.util.CommonUtil;

@Service
public class NegotiationService {

	private static final Logger logger = LoggerFactory.getLogger(NegotiationService.class);

	@Autowired
	private NegotiationRepository negotiationRepository;

	public Page<Negotiation> findByStatus(String status, Pageable pageable) {
		logger.info("NegotiationService - findByStatus: " + status);

		if (status == null || !status.isEmpty()) {
			return negotiationRepository.findAll(pageable);
		}
		return negotiationRepository.findByStatus(status, pageable);
	}

	public Negotiation getNegotiationById(Long id) {
		logger.info("getNegotiationById: " + id);
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
		Negotiation negotiation = getNegotiationById(id);

		if (negotiation != null) {
			logger.info("negotiation is not null");

			if (!negotiationRequest.getUpdatedAt().equals(negotiation.getUpdatedAt())) {
				throw new ResourceConflictException("Record has been modified by another user.");
			}

			negotiation.setCounterHourlyRate(negotiationRequest.getCounterHourlyRate());
			negotiation.setCounterTotalPaid(negotiationRequest.getCounterTotalPaid());
			negotiation.setUpdatedAt(LocalDateTime.now()); // Set current time for update
			negotiation.setUpdatedUserId(CommonUtil.getCurrentUserId()); // Retrieve from the current session

			return negotiationRepository.save(negotiation);
		} else {
			logger.info("negotiation is null");

			throw new ResourceNotFoundException("Negotiation not found with ID " + id);

		}
	}
}
