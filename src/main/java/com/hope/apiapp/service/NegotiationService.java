package com.hope.apiapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hope.apiapp.dto.NegotiationRequest;
import com.hope.apiapp.exception.ResourceNotFoundException;
import com.hope.apiapp.model.Negotiation;
import com.hope.apiapp.repository.NegotiationRepository;
import com.hope.apiapp.util.CommonUtil;

@Service
public class NegotiationService {

	private static final Logger logger = LoggerFactory.getLogger(NegotiationService.class);

	@Autowired
	private NegotiationRepository negotiationRepository;

	public List<Negotiation> getAllNegotiations() {
		logger.info("NegotiationProjection - findAllNegotiationsWithLimitedFields");
		return negotiationRepository.findAll();
	}

	public Negotiation getNegotiationById(Long id) {
		logger.info("getNegotiationById: " + id);
		return negotiationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Negotiation not found"));
	}

	public Negotiation getNegotiationByIdWithLimitedFields(Long id) {
		logger.info("getNegotiationByIdWithLimitedFields: " + id);
		return negotiationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Negotiation not found"));
	}

	public Negotiation addNegotiation(NegotiationRequest negotiationRequest) {
		logger.info("addNegotiation");

		Negotiation negotiation = new Negotiation();

		// Set default values for additional fields
		negotiation.setStatus("New"); // Default status
		negotiation.setUpdatedUserId(CommonUtil.getCurrentUserId()); // Retrieve from the current session
		negotiation.setCreatedAt(LocalDateTime.now()); // Set current time for creation
		negotiation.setUpdatedAt(LocalDateTime.now()); // Set current time for update

		return negotiationRepository.save(negotiation);
	}

	public Negotiation updateNegotiation(Long id, NegotiationRequest negotiationRequest) {

		logger.info("updateNegotiation: " + id);
		Negotiation negotiation = getNegotiationById(id);

//		// TODO:Parse the Last-Modified header
//		Date clientLastModified = new Date(lastModified);
//		if (clientLastModified.before(negotiation.getUpdatedAt())) {
//			throw new ResourceConflictException("Job has been modified by another user.");
//		}

		if (negotiation != null) {
			logger.info("negotiation is not null");

			negotiation.setUpdatedAt(LocalDateTime.now()); // Set current time for update
			negotiation.setUpdatedUserId(CommonUtil.getCurrentUserId()); // Retrieve from the current session

			return negotiationRepository.save(negotiation);
		} else {
			logger.info("negotiation is null");

			throw new ResourceNotFoundException("Negotiation not found with ID " + id);

		}
	}
}
