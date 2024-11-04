package com.hope.apiapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hope.apiapp.model.PharmacyBranch;
import com.hope.apiapp.service.PharmacyBranchService;

@RestController
@RequestMapping("/api")
@Validated
public class PharmacyBranchController {

	@Autowired
	private PharmacyBranchService pharmacyBranchService;

	@GetMapping("/v1/branch")
	public ResponseEntity<ApiResponseSuccess<List<PharmacyBranch>>> getAllBranches() {
		List<PharmacyBranch> branchs =  pharmacyBranchService.findAllBranches();
		
		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", branchs), HttpStatus.OK);
	}

	@GetMapping("/v1/branch/{id}")
	public ResponseEntity<ApiResponseSuccess<PharmacyBranch>>> getBranchById(@PathVariable Long id) {
		PharmacyBranch branch = pharmacyBranchService.findBranchById(id);
		
		return new ResponseEntity<>(new ApiResponseSuccess<>("1.0", branch), HttpStatus.OK);
	}

	@PostMapping("/v1/branch")
	public ResponseEntity<ApiResponseSuccess<PharmacyBranch>>> addBranch(@RequestBody PharmacyBranch pharmacyBranch) {
		PharmacyBranch branch = pharmacyBranchService.addBranch(pharmacyBranch);
		
		return ResponseEntity<>(new ApiResponseSuccess<>("1.0", updateBranch.getPharmacyBranchId()),
				HttpStatus.CREATED);
	}

	@PutMapping("/v1/branch/{id}")
	public ResponseEntity<ApiResponseSuccess<PharmacyBranch>>> updateBranch(@PathVariable Long id,
			@RequestBody PharmacyBranch branchDetails) {
		PharmacyBranch updatedBranch = pharmacyBranchService.updateBranch(id, branchDetails);
		
		return ResponseEntity<>(new ApiResponseSuccess<>("1.0", updateBranch.getPharmacyBranchId()),
				HttpStatus.OK);
	}
}
