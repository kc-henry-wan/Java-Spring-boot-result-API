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
@RequestMapping("/PharmacyBranchs")
public class PharmacyBranchController {

	@Autowired
	private PharmacyBranchService pharmacyBranchService;

	@GetMapping
	public List<PharmacyBranch> getAllBranches() {
		return pharmacyBranchService.findAllBranches();
	}

	@PostMapping
	public PharmacyBranch createBranch(@RequestBody PharmacyBranch pharmacyBranch) {
		return pharmacyBranchService.saveBranch(pharmacyBranch);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PharmacyBranch> updateBranch(@PathVariable Long id,
			@RequestBody PharmacyBranch branchDetails) {
		PharmacyBranch updatedBranch = pharmacyBranchService.updateBranch(id, branchDetails);
		return ResponseEntity.ok(updatedBranch);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PharmacyBranch> getBranchById(@PathVariable Long id) {
		PharmacyBranch branch = pharmacyBranchService.findBranchById(id)
				.orElseThrow(() -> new RuntimeException("Branch not found"));
		return ResponseEntity.ok(branch);
	}
}
