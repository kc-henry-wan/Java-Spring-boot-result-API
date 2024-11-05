package com.hope.apiapp.controller;

import java.util.List;

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

import com.hope.apiapp.dto.PharmacyBranchRequestDto;
import com.hope.apiapp.helper.ApiResponseSuccess;
import com.hope.apiapp.model.PharmacyBranch;
import com.hope.apiapp.service.PharmacyBranchService;

@RestController
@RequestMapping("/api")
@Validated
public class PharmacyBranchController {

	@Autowired
	private PharmacyBranchService pharmacyBranchService;

	@GetMapping("/v1/branch")
	public ResponseEntity<ApiResponseSuccess<List<PharmacyBranch>>> findAllBranches() {
		List<PharmacyBranch> branchs = pharmacyBranchService.findAllBranches();

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", branchs), HttpStatus.OK);
	}

	@GetMapping("/v1/branch/{id}")
	public ResponseEntity<ApiResponseSuccess<PharmacyBranch>> findBranchById(@PathVariable Long id) {
		PharmacyBranch branch = pharmacyBranchService.findBranchById(id);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", branch), HttpStatus.OK);
	}

	@PostMapping("/v1/branch")
	public ResponseEntity<ApiResponseSuccess<Long>> addPharmacyBranch(
			@RequestBody PharmacyBranchRequestDto pharmacyBranch) {
		PharmacyBranch branch = pharmacyBranchService.addPharmacyBranch(pharmacyBranch);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", branch.getPharmacyBranchId()), HttpStatus.CREATED);
	}

	@PutMapping("/v1/branch/{id}")
	public ResponseEntity<ApiResponseSuccess<Long>> updatePharmacyBranch(@PathVariable Long id,
			@RequestBody PharmacyBranchRequestDto branchDetails) {
		PharmacyBranch updatedBranch = pharmacyBranchService.updatePharmacyBranch(id, branchDetails);

		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", updatedBranch.getPharmacyBranchId()),
				HttpStatus.OK);
	}
}
