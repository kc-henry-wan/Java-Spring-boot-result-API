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
import org.springframework.web.bind.annotation.RestController;

import com.hope.apiapp.dto.NegotiationRequest;
import com.hope.apiapp.model.Negotiation;
import com.hope.apiapp.service.NegotiationService;
import com.hope.apiapp.util.ApiResponseSuccess;

@RestController
@RequestMapping("/api")
@Validated
//@CrossOrigin(origins = "http://localhost:73")

public class NegotiationController {

	private static final Logger logger = LoggerFactory.getLogger(NegotiationController.class);

	private final NegotiationService negotiationService;

	@Autowired
	public NegotiationController(NegotiationService negotiationService) {
		this.negotiationService = negotiationService;
	}

	@GetMapping("/v1/negotiation")
	public ResponseEntity<ApiResponseSuccess<List<Negotiation>>> getAllNegotiations() {
		List<Negotiation> negotiations = negotiationService.getAllNegotiations();
		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", negotiations), HttpStatus.OK);
	}

	@GetMapping("/v1/negotiation/{id}")
	public ResponseEntity<ApiResponseSuccess<Negotiation>> getNegotiationById(@PathVariable Long id) {
		logger.info("getNegotiationById");

		Negotiation negotiation = negotiationService.getNegotiationByIdWithLimitedFields(id);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", negotiation), HttpStatus.OK);
	}

	@PostMapping("/v1/negotiation")
	public ResponseEntity<ApiResponseSuccess<Long>> addNegotiation(@RequestBody NegotiationRequest negotiationRequest) {
		logger.info("addNegotiation");

		Negotiation createdNegotiation = negotiationService.addNegotiation(negotiationRequest);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", createdNegotiation.getNegotiationId()),
				HttpStatus.CREATED);
	}

	@PutMapping("/v1/negotiation/{id}")
	public ResponseEntity<ApiResponseSuccess<Long>> updateNegotiation(@PathVariable Long id,
			@RequestBody NegotiationRequest negotiationRequest) {
		logger.info("updateNegotiation");

		Negotiation updatedNegotiation = negotiationService.updateNegotiation(id, negotiationRequest);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", updatedNegotiation.getNegotiationId()),
				HttpStatus.OK);
	}

}
