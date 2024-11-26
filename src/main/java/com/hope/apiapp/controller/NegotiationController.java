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

import com.hope.apiapp.dto.NegotiationAcceptRequestDto;
import com.hope.apiapp.dto.NegotiationAddRequestDto;
import com.hope.apiapp.dto.NegotiationDto;
import com.hope.apiapp.dto.NegotiationProjection;
import com.hope.apiapp.dto.NegotiationUpdateRequestDto;
import com.hope.apiapp.helper.ApiResponseSuccess;
import com.hope.apiapp.model.Negotiation;
import com.hope.apiapp.service.NegotiationService;

@RestController
@RequestMapping("/api")
@Validated
public class NegotiationController {

	private static final Logger logger = LoggerFactory.getLogger(NegotiationController.class);

	private final NegotiationService negotiationService;

	@Autowired
	public NegotiationController(NegotiationService negotiationService) {
		this.negotiationService = negotiationService;
	}

	@GetMapping("/v1/negotiation")
	public ResponseEntity<ApiResponseSuccess<Page<NegotiationDto>>> getNegotiations(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size,
			@RequestParam(required = false) String status, @RequestParam(required = false) Long pharmacistId) {

		Pageable pageable = PageRequest.of(page, size);

		Page<NegotiationDto> negotiations = negotiationService.getNegotiations(pageable, status, pharmacistId);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", negotiations), HttpStatus.OK);
	}

	@GetMapping("/v1/negotiation/{id}")
	public ResponseEntity<ApiResponseSuccess<NegotiationProjection>> getNegotiationById(@PathVariable Long id) {

		NegotiationProjection negotiation = negotiationService.getNegotiationById(id);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", negotiation), HttpStatus.OK);
	}

	@PostMapping("/v1/negotiation")
	public ResponseEntity<ApiResponseSuccess<Long>> addNegotiation(
			@RequestBody NegotiationAddRequestDto negotiationRequest) {

		Negotiation createdNegotiation = negotiationService.addNegotiation(negotiationRequest);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", createdNegotiation.getNegotiationId()),
				HttpStatus.CREATED);
	}

	@PutMapping("/v1/negotiation/{id}")
	public ResponseEntity<ApiResponseSuccess<Long>> acceptNegotiation(@PathVariable Long id,
			@RequestBody NegotiationAcceptRequestDto negotiationRequest) {

		Negotiation updatedNegotiation = negotiationService.acceptNegotiation(id, negotiationRequest);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", updatedNegotiation.getNegotiationId()),
				HttpStatus.OK);
	}

	@PutMapping("/staff/v1/negotiation/{id}")
	public ResponseEntity<ApiResponseSuccess<Long>> updateNegotiation(@PathVariable Long id,
			@RequestBody NegotiationUpdateRequestDto negotiationRequest) {

		Negotiation updatedNegotiation = negotiationService.updateNegotiation(id, negotiationRequest);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", updatedNegotiation.getNegotiationId()),
				HttpStatus.OK);
	}

}
