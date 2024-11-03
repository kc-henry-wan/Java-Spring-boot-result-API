package com.hope.apiapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hope.apiapp.model.PharmacyBranch;
import com.hope.apiapp.repository.PharmacyBranchRepository;

@Service
public class PharmacyBranchService {

	@Autowired
	private PharmacyBranchRepository pharmacyBranchRepository;

	public List<PharmacyBranch> findAllBranches() {
		return pharmacyBranchRepository.findAll();
	}

	public Optional<PharmacyBranch> findBranchById(Long id) {
		return pharmacyBranchRepository.findById(id);
	}

	public PharmacyBranch saveBranch(PharmacyBranch branch) {
		return pharmacyBranchRepository.save(branch);
	}

	public PharmacyBranch updateBranch(Long id, PharmacyBranch branchDetails) {
		PharmacyBranch branch = pharmacyBranchRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Branch not found"));

		branch.setBranchName(branchDetails.getBranchName());
		branch.setAddress1(branchDetails.getAddress1());
		branch.setAddress2(branchDetails.getAddress2());
		branch.setPostalCode(branchDetails.getPostalCode());
		branch.setLongitude(branchDetails.getLongitude());
		branch.setLatitude(branchDetails.getLatitude());
		branch.setStatus(branchDetails.getStatus());
		branch.setUpdatedUserId(branchDetails.getUpdatedUserId());

		return pharmacyBranchRepository.save(branch);
	}
}
