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

import com.hope.apiapp.dto.PharmacistProjection;
import com.hope.apiapp.dto.PharmacistRequest;
import com.hope.apiapp.model.Pharmacist;
import com.hope.apiapp.service.PharmacistService;
import com.hope.apiapp.util.ApiResponseSuccess;

@RestController
@RequestMapping("/api")
@Validated
//@CrossOrigin(origins = "http://localhost:73")

public class PharmacistController {

	private static final Logger logger = LoggerFactory.getLogger(PharmacistController.class);

	private final PharmacistService pharmacistService;

	@Autowired
	public PharmacistController(PharmacistService pharmacistService) {
		this.pharmacistService = pharmacistService;
	}

	@GetMapping("/v1/pharmacist")
	public ResponseEntity<ApiResponseSuccess<List<PharmacistProjection>>> getAllPharmacists() {
		List<PharmacistProjection> pharmacists = pharmacistService.getAllPharmacists();
		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", pharmacists), HttpStatus.OK);
	}

	@GetMapping("/v1/pharmacist/{id}")
	public ResponseEntity<ApiResponseSuccess<PharmacistProjection>> getPharmacistById(@PathVariable Long id) {
		logger.info("getPharmacistById");

		PharmacistProjection pharmacist = pharmacistService.getPharmacistByIdWithLimitedFields(id);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", pharmacist), HttpStatus.OK);
	}

	@PostMapping("/v1/pharmacist")
	public ResponseEntity<ApiResponseSuccess<Long>> addPharmacist(@RequestBody PharmacistRequest pharmacistRequest) {
		logger.info("addPharmacist");

		Pharmacist createdPharmacist = pharmacistService.addPharmacist(pharmacistRequest);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", createdPharmacist.getPharmacistId()),
				HttpStatus.CREATED);
	}

	@PutMapping("/v1/pharmacist/{id}")
	public ResponseEntity<ApiResponseSuccess<Long>> updatePharmacist(@PathVariable Long id,
			@RequestBody PharmacistRequest pharmacistRequest) {
		logger.info("updatePharmacist");

		Pharmacist updatedPharmacist = pharmacistService.updatePharmacist(id, pharmacistRequest);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", updatedPharmacist.getPharmacistId()),
				HttpStatus.OK);
	}

}
