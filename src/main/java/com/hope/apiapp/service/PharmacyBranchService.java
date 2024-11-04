package com.hope.apiapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hope.apiapp.dto.PharmacyBranchRequestDto;
import com.hope.apiapp.exception.ResourceNotFoundException;
import com.hope.apiapp.model.PharmacyBranch;
import com.hope.apiapp.repository.PharmacyBranchRepository;
import com.hope.apiapp.util.CommonUtil;

@Service
public class PharmacyBranchService {

	private static final Logger logger = LoggerFactory.getLogger(PharmacyBranchService.class);

	@Autowired
	private PharmacyBranchRepository pharmacyBranchRepository;

	public List<PharmacyBranch> findAllBranches() {
		return pharmacyBranchRepository.findAll();
	}

	public PharmacyBranch findBranchById(Long id) {
		return pharmacyBranchRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Brance not found"));
	}

	public PharmacyBranch addPharmacyBranch(PharmacyBranchRequestDto branchReq) {

		// Build the full address for the API call
		String fullAddress = branchReq.getAddress1() + " " + branchReq.getAddress2() + " " + branchReq.getPostalCode();
		double[] coordinates = CommonUtil.getCoordinatesFromAddress(fullAddress);

		PharmacyBranch branch = new PharmacyBranch();

		branch.setBranchName(branchReq.getBranchName());
		branch.setAddress1(branchReq.getAddress1());
		branch.setAddress2(branchReq.getAddress2());
		branch.setPostalCode(branchReq.getPostalCode());
		branch.setLongitude(coordinates[0]);
		branch.setLatitude(coordinates[1]);
		// Set default values for additional fields
		branch.setStatus("Active"); // Default status
		branch.setUpdatedUserId(CommonUtil.getCurrentUserId()); // Retrieve from the current session
		branch.setCreatedAt(LocalDateTime.now()); // Set current time for creation
		branch.setUpdatedAt(LocalDateTime.now()); // Set current time for update

		return pharmacyBranchRepository.save(branch);
	}

	public PharmacyBranch updatePharmacyBranch(Long id, PharmacyBranchRequestDto branchDetails) {
		PharmacyBranch branch = findBranchById(id);

		if (branch != null) {
			logger.info("Branch is NOT null");
			// Build the full address for the API call
			String fullAddress = branchDetails.getAddress1() + " " + branchDetails.getAddress2() + " "
					+ branchDetails.getPostalCode();
			double[] coordinates = CommonUtil.getCoordinatesFromAddress(fullAddress);

			branch.setBranchName(branchDetails.getBranchName());
			branch.setAddress1(branchDetails.getAddress1());
			branch.setAddress2(branchDetails.getAddress2());
			branch.setPostalCode(branchDetails.getPostalCode());
			branch.setLongitude(coordinates[0]);
			branch.setLatitude(coordinates[1]);
			branch.setStatus(branchDetails.getStatus());

			branch.setUpdatedAt(LocalDateTime.now()); // Set current time for update
			branch.setUpdatedUserId(CommonUtil.getCurrentUserId()); // Retrieve from the current session

			return pharmacyBranchRepository.save(branch);
		} else {
			logger.info("Branch is null");

			throw new ResourceNotFoundException("Branch not found with ID " + id);

		}
	}
}
