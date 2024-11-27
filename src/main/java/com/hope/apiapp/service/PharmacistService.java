package com.hope.apiapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hope.apiapp.dto.PharmacistAddRequestDto;
import com.hope.apiapp.dto.PharmacistDto;
import com.hope.apiapp.dto.PharmacistProjection;
import com.hope.apiapp.dto.PharmacistUpdateRequestDto;
import com.hope.apiapp.exception.ResourceConflictException;
import com.hope.apiapp.exception.ResourceNotFoundException;
import com.hope.apiapp.model.Pharmacist;
import com.hope.apiapp.repository.PharmacistRepository;
import com.hope.apiapp.util.CommonUtil;

@Service
public class PharmacistService {

	private static final Logger logger = LoggerFactory.getLogger(PharmacistService.class);

	private static final String defaultRole = "ROLE_USER";

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private PharmacistRepository pharmacistRepository;

	@Autowired
	private PasswordResetTokenService passwordResetTokenService;

	@Autowired
	private CommonUtil commonUtil;

	public Page<PharmacistDto> searchPharmacists(Pageable pageable, String term, String status) {
		return pharmacistRepository.searchPharmacists(pageable, term, status);

	}

	public Pharmacist getPharmacistById(Long id) {
		return pharmacistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("RNF-P001-" + id));
	}

	public PharmacistProjection getPharmacistByIdWithLimitedFields(Long id) {
		return pharmacistRepository.findByIdWithLimitedFields(id)
				.orElseThrow(() -> new ResourceNotFoundException("RNF-P002-" + id));
	}

	public Pharmacist addPharmacist(PharmacistAddRequestDto pharmacistRequest) {
		String fullAddress = pharmacistRequest.getAddress1() + " " + pharmacistRequest.getAddress2() + " "
				+ pharmacistRequest.getPostalCode();
		Double[] coordinates = commonUtil.getCoordinatesFromAddress(fullAddress);

		Pharmacist pharmacist = new Pharmacist();
		pharmacist.setPassword(passwordEncoder.encode(pharmacistRequest.getPassword()));
		pharmacist.setEmail(pharmacistRequest.getEmail());
		pharmacist.setFirstName(pharmacistRequest.getFirstName());
		pharmacist.setLastName(pharmacistRequest.getLastName());
		pharmacist.setMobile(pharmacistRequest.getMobile());
		pharmacist.setAddress1(pharmacistRequest.getAddress1());
		pharmacist.setAddress2(pharmacistRequest.getAddress2());
		pharmacist.setPostalCode(pharmacistRequest.getPostalCode());
		pharmacist.setLatitude(coordinates[0]);
		pharmacist.setLongitude(coordinates[1]);
		pharmacist.setRole(defaultRole);

		// Set default values for additional fields
		pharmacist.setStatus("Pending"); // Default status, change to active after call activate API
		pharmacist.setUpdatedUserId(commonUtil.getCurrentUserId()); // Retrieve from the current session
		pharmacist.setCreatedAt(LocalDateTime.now()); // Set current time for creation
		pharmacist.setUpdatedAt(LocalDateTime.now()); // Set current time for update

		Pharmacist newPharmacist = pharmacistRepository.save(pharmacist);

		passwordResetTokenService.generateResetToken(newPharmacist.getEmail(), true);

		return newPharmacist;
	}

	public Pharmacist updatePharmacist(Long id, PharmacistUpdateRequestDto pharmacistRequest) {
		Pharmacist pharmacist = getPharmacistById(id);

		if (pharmacist != null) {
			if (!pharmacistRequest.getUpdatedAt().equals(pharmacist.getUpdatedAt())) {
				throw new ResourceConflictException("RCE-P401");
			}

			// Build the full address for the API call
			String fullAddress = pharmacistRequest.getAddress1() + " " + pharmacistRequest.getAddress2() + " "
					+ pharmacistRequest.getPostalCode();
			Double[] coordinates = commonUtil.getCoordinatesFromAddress(fullAddress);

			// Password and email not allow to update
			pharmacist.setFirstName(pharmacistRequest.getFirstName());
			pharmacist.setLastName(pharmacistRequest.getLastName());
			pharmacist.setMobile(pharmacistRequest.getMobile());
			pharmacist.setAddress1(pharmacistRequest.getAddress1());
			pharmacist.setAddress2(pharmacistRequest.getAddress2());
			pharmacist.setPostalCode(pharmacistRequest.getPostalCode());
			pharmacist.setLatitude(coordinates[0]);
			pharmacist.setLongitude(coordinates[1]);
			pharmacist.setUpdatedAt(LocalDateTime.now()); // Set current time for update
			pharmacist.setUpdatedUserId(commonUtil.getCurrentUserId()); // Retrieve from the current session

			return pharmacistRepository.save(pharmacist);
		} else {
			throw new ResourceNotFoundException("RNF-P003- " + id);
		}
	}

	public Boolean activatePharmacist(Long id) {
		Pharmacist pharmacist = getPharmacistById(id);

		if (pharmacist != null) {
			pharmacist.setStatus("Active");
			pharmacist.setUpdatedAt(LocalDateTime.now());
			pharmacist.setUpdatedUserId(commonUtil.getCurrentUserId()); // Retrieve from the current session

			pharmacistRepository.save(pharmacist);

			return true;
		} else {
			throw new ResourceNotFoundException("RNF-P004-" + id);
		}
	}

	public boolean resetPassword(Long id, String newPassword) {
		Pharmacist pharmacist = getPharmacistById(id);

		if (pharmacist != null) {
			pharmacist.setPassword(passwordEncoder.encode(newPassword));
			pharmacist.setUpdatedAt(LocalDateTime.now());
			pharmacist.setUpdatedUserId(commonUtil.getCurrentUserId()); // Retrieve from the current session

			pharmacistRepository.save(pharmacist);

			return true;
		} else {
			throw new ResourceNotFoundException("RNF-P005-" + id);
		}
	}

	public Integer updateMissingCoordinates() {
		List<Pharmacist> pharmacists = pharmacistRepository.findPharmacistsWithMissingCoordinates();

		for (Pharmacist pharmacist : pharmacists) {
			String fullAddress = pharmacist.getAddress1() + " " + pharmacist.getAddress2() + " "
					+ pharmacist.getPostalCode();

			try {
				Double[] coordinates = commonUtil.getCoordinatesFromAddress(fullAddress);

				if (coordinates != null && coordinates.length == 2) {
					pharmacist.setLatitude(coordinates[0]);
					pharmacist.setLongitude(coordinates[1]);
				}
			} catch (Exception e) {
				// Log error and skip the branch if API call fails
				logger.info("Failed to update coordinates for branch ID " + pharmacist.getPharmacistId());
			}
		}

		// Save all updated branches
		pharmacistRepository.saveAll(pharmacists);

		return pharmacists.size();
	}
}
