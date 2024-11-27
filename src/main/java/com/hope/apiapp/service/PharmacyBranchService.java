package com.hope.apiapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hope.apiapp.dto.PharmacyBranchRequestDto;
import com.hope.apiapp.exception.ResourceConflictException;
import com.hope.apiapp.exception.ResourceNotFoundException;
import com.hope.apiapp.model.PharmacyBranch;
import com.hope.apiapp.repository.PharmacyBranchRepository;
import com.hope.apiapp.util.CommonUtil;

@Service
public class PharmacyBranchService {

	private static final Logger logger = LoggerFactory.getLogger(PharmacyBranchService.class);

	@Autowired
	private PharmacyBranchRepository pharmacyBranchRepository;

	@Autowired
	private CommonUtil commonUtil;

	public Page<PharmacyBranch> findByStatus(String status, Pageable pageable) {
		if (status == null || !status.isEmpty()) {
			return pharmacyBranchRepository.findAll(pageable);
		}
		return pharmacyBranchRepository.findByStatus(status, pageable);
	}

	public PharmacyBranch findBranchById(Long id) {
		return pharmacyBranchRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("RNF-B003-" + id));
	}

	public PharmacyBranch addPharmacyBranch(PharmacyBranchRequestDto branchReq) {

		// Build the full address for the API call
		String fullAddress = branchReq.getAddress1() + " " + branchReq.getAddress2() + " " + branchReq.getPostalCode();
		Double[] coordinates = commonUtil.getCoordinatesFromAddress(fullAddress);

		PharmacyBranch branch = new PharmacyBranch();

		branch.setBranchName(branchReq.getBranchName());
		branch.setAddress1(branchReq.getAddress1());
		branch.setAddress2(branchReq.getAddress2());
		branch.setPostalCode(branchReq.getPostalCode());
		branch.setLatitude(coordinates[0]);
		branch.setLongitude(coordinates[1]);
		// Set default values for additional fields
		branch.setStatus("Active"); // Default status
		branch.setUpdatedUserId(commonUtil.getCurrentUserId()); // Retrieve from the current session
		branch.setCreatedAt(LocalDateTime.now()); // Set current time for creation
		branch.setUpdatedAt(LocalDateTime.now()); // Set current time for update

		return pharmacyBranchRepository.save(branch);
	}

	public PharmacyBranch updatePharmacyBranch(Long id, PharmacyBranchRequestDto branchReq) {
		PharmacyBranch branch = findBranchById(id);

		if (branch != null) {
			if (!branchReq.getUpdatedAt().equals(branch.getUpdatedAt())) {
				throw new ResourceConflictException("RCE-B401");
			}

			// Build the full address for the API call
			String fullAddress = branchReq.getAddress1() + " " + branchReq.getAddress2() + " "
					+ branchReq.getPostalCode();
			Double[] coordinates = commonUtil.getCoordinatesFromAddress(fullAddress);

			branch.setBranchName(branchReq.getBranchName());
			branch.setAddress1(branchReq.getAddress1());
			branch.setAddress2(branchReq.getAddress2());
			branch.setPostalCode(branchReq.getPostalCode());
			branch.setLatitude(coordinates[0]);
			branch.setLongitude(coordinates[1]);
			branch.setStatus(branchReq.getStatus());

			branch.setUpdatedAt(LocalDateTime.now()); // Set current time for update
			branch.setUpdatedUserId(commonUtil.getCurrentUserId()); // Retrieve from the current session

			return pharmacyBranchRepository.save(branch);
		} else {
			throw new ResourceNotFoundException("RNF-B002-" + id);

		}
	}

	public Integer updateMissingCoordinates() {
		List<PharmacyBranch> branches = pharmacyBranchRepository.findBranchesWithMissingCoordinates();

		for (PharmacyBranch branch : branches) {
			String fullAddress = branch.getAddress1() + " " + branch.getAddress2() + " " + branch.getPostalCode();

			try {
				Double[] coordinates = commonUtil.getCoordinatesFromAddress(fullAddress);

				if (coordinates != null && coordinates.length == 2) {
					branch.setLatitude(coordinates[0]);
					branch.setLongitude(coordinates[1]);
				}
			} catch (Exception e) {
				// Log error and skip the branch if API call fails
				logger.info("Failed to update coordinates for branch ID " + branch.getPharmacyBranchId());
			}
		}

		// Save all updated branches
		pharmacyBranchRepository.saveAll(branches);

		return branches.size();
	}
}
