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

import com.hope.apiapp.dto.PharmacistAddRequestDto;
import com.hope.apiapp.dto.PharmacistDto;
import com.hope.apiapp.dto.PharmacistProjection;
import com.hope.apiapp.dto.PharmacistUpdateRequestDto;
import com.hope.apiapp.exception.ResourceNotFoundException;
import com.hope.apiapp.helper.ApiResponseSuccess;
import com.hope.apiapp.model.PasswordResetToken;
import com.hope.apiapp.model.Pharmacist;
import com.hope.apiapp.service.PasswordResetTokenService;
import com.hope.apiapp.service.PharmacistService;
import com.hope.apiapp.util.CommonUtil;

@RestController
@RequestMapping("/api")
@Validated

public class PharmacistController {

	private static final Logger logger = LoggerFactory.getLogger(PharmacistController.class);

	private final PharmacistService pharmacistService;
	private final CommonUtil commonUtil;
	private final PasswordResetTokenService passwordResetService;

	@Autowired
	public PharmacistController(PharmacistService pharmacistService, PasswordResetTokenService passwordResetService,
			CommonUtil commonUtil) {
		this.pharmacistService = pharmacistService;
		this.passwordResetService = passwordResetService;
		this.commonUtil = commonUtil;
	}

	@GetMapping("/admin/v1/pharmacist")
	public ResponseEntity<ApiResponseSuccess<Page<PharmacistDto>>> searchPharmacists(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size,
			@RequestParam(required = false) String term, @RequestParam(required = false) String status) {

		Pageable pageable = PageRequest.of(page, size);

		Page<PharmacistDto> pharmacists = pharmacistService.searchPharmacists(pageable, term, status);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", pharmacists), HttpStatus.OK);
	}

	@GetMapping("/v1/myprofile")
	public ResponseEntity<ApiResponseSuccess<PharmacistProjection>> getMyProfile() {

		Long userId = commonUtil.getCurrentUserId();
		PharmacistProjection pharmacist = pharmacistService.getPharmacistByIdWithLimitedFields(userId);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", pharmacist), HttpStatus.OK);
	}

	@GetMapping("/admin/v1/pharmacist/{id}")
	public ResponseEntity<ApiResponseSuccess<PharmacistProjection>> getPharmacistByIdWithLimitedFields(
			@PathVariable Long id) {

		PharmacistProjection pharmacist = pharmacistService.getPharmacistByIdWithLimitedFields(id);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", pharmacist), HttpStatus.OK);
	}

	@PostMapping("/auth/v1/register")
	public ResponseEntity<ApiResponseSuccess<Long>> addPharmacist(
			@RequestBody PharmacistAddRequestDto pharmacistRequest) {

		Pharmacist createdPharmacist = pharmacistService.addPharmacist(pharmacistRequest);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", createdPharmacist.getPharmacistId()),
				HttpStatus.CREATED);
	}

	@PostMapping("/auth/v1/activate/{id}")
	public ResponseEntity<ApiResponseSuccess<Long>> activatePharmacist(@PathVariable Long id,
			@RequestParam String token) {

		PasswordResetToken resetToken = passwordResetService.validatePasswordResetToken(token);

		if (resetToken != null) {
			Boolean activateResult = pharmacistService.activatePharmacist(id);

			if (activateResult) {
				passwordResetService.invalidateToken(resetToken); // Invalidate token after use

				return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", id), HttpStatus.CREATED);
			} else {
				throw new ResourceNotFoundException("RNF-P201-" + id);
			}
		} else {
			throw new ResourceNotFoundException("RNF-P202-" + id);
		}
	}

	@PutMapping("/v1/myprofile")
	public ResponseEntity<ApiResponseSuccess<Long>> updateMyProfile(
			@RequestBody PharmacistUpdateRequestDto pharmacistRequest) {

		Long userId = commonUtil.getCurrentUserId();
		Pharmacist updatedPharmacist = pharmacistService.updatePharmacist(userId, pharmacistRequest);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", updatedPharmacist.getPharmacistId()),
				HttpStatus.OK);
	}

	@PutMapping("/admin/v1/pharmacist/{id}")
	public ResponseEntity<ApiResponseSuccess<Long>> updatePharmacist(@PathVariable Long id,
			@RequestBody PharmacistUpdateRequestDto pharmacistRequest) {

		Pharmacist updatedPharmacist = pharmacistService.updatePharmacist(id, pharmacistRequest);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", updatedPharmacist.getPharmacistId()),
				HttpStatus.OK);
	}

	@GetMapping("/admin/v1/pharmacist/coordinates")
	public ResponseEntity<ApiResponseSuccess<Integer>> updateMissingCoordinates() {
		Integer count = pharmacistService.updateMissingCoordinates();

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", count), HttpStatus.OK);
	}
}
