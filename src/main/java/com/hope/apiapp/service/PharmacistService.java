package com.hope.apiapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hope.apiapp.dto.PharmacistAddRequestDto;
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

//	public List<Pharmacist> getAllPharmacists() {
//		logger.info("getAllPharmacists");
//		return pharmacistRepository.findAll();
//	}

	public List<PharmacistProjection> getAllPharmacists() {
		logger.info("PharmacistProjection - findAllPharmacistsWithLimitedFields");
		return pharmacistRepository.findAllPharmacistsWithLimitedFields();
	}

	public Pharmacist getPharmacistById(Long id) {
		logger.info("getPharmacistById: " + id);
		return pharmacistRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pharmacist not found"));
	}

	public PharmacistProjection getPharmacistByIdWithLimitedFields(Long id) {
		logger.info("getPharmacistByIdWithLimitedFields: " + id);
		return pharmacistRepository.findPharmacistByIdWithLimitedFields(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pharmacist not found"));
	}

	public Pharmacist addPharmacist(PharmacistAddRequestDto pharmacistRequest) {
		logger.info("addPharmacist");

		// Build the full address for the API call
		String fullAddress = pharmacistRequest.getAddress1() + " " + pharmacistRequest.getAddress2() + " "
				+ pharmacistRequest.getPostalCode();
		double[] coordinates = CommonUtil.getCoordinatesFromAddress(fullAddress);

		Pharmacist pharmacist = new Pharmacist();
		pharmacist.setPassword(passwordEncoder.encode(pharmacistRequest.getPassword()));
		pharmacist.setEmail(pharmacistRequest.getEmail());
		pharmacist.setFirstName(pharmacistRequest.getFirstName());
		pharmacist.setLastName(pharmacistRequest.getLastName());
		pharmacist.setMobile(pharmacistRequest.getMobile());
		pharmacist.setAddress1(pharmacistRequest.getAddress1());
		pharmacist.setAddress2(pharmacistRequest.getAddress2());
		pharmacist.setPostalCode(pharmacistRequest.getPostalCode());
		pharmacist.setLongitude(coordinates[0]);
		pharmacist.setLatitude(coordinates[1]);
		pharmacist.setRole(defaultRole);

		// Set default values for additional fields
		pharmacist.setStatus("Active"); // Default status
		pharmacist.setUpdatedUserId(CommonUtil.getCurrentUserId()); // Retrieve from the current session
		pharmacist.setCreatedAt(LocalDateTime.now()); // Set current time for creation
		pharmacist.setUpdatedAt(LocalDateTime.now()); // Set current time for update

		return pharmacistRepository.save(pharmacist);
	}

	public Pharmacist updatePharmacist(Long id, PharmacistUpdateRequestDto pharmacistRequest) {

		logger.info("updatePharmacist: " + id);
		Pharmacist pharmacist = getPharmacistById(id);

		if (pharmacist != null) {
			logger.info("pharmacist is not null");

			if (!pharmacistRequest.getUpdatedAt().equals(pharmacist.getUpdatedAt())) {
				throw new ResourceConflictException("Record has been modified by another user.");
			}

			// Build the full address for the API call
			String fullAddress = pharmacistRequest.getAddress1() + " " + pharmacistRequest.getAddress2() + " "
					+ pharmacistRequest.getPostalCode();
			double[] coordinates = CommonUtil.getCoordinatesFromAddress(fullAddress);

			// Password and email not allow to update
			pharmacist.setFirstName(pharmacistRequest.getFirstName());
			pharmacist.setLastName(pharmacistRequest.getLastName());
			pharmacist.setMobile(pharmacistRequest.getMobile());
			pharmacist.setAddress1(pharmacistRequest.getAddress1());
			pharmacist.setAddress2(pharmacistRequest.getAddress2());
			pharmacist.setPostalCode(pharmacistRequest.getPostalCode());
			pharmacist.setLongitude(coordinates[0]);
			pharmacist.setLatitude(coordinates[1]);
			pharmacist.setUpdatedAt(LocalDateTime.now()); // Set current time for update
			pharmacist.setUpdatedUserId(CommonUtil.getCurrentUserId()); // Retrieve from the current session

			return pharmacistRepository.save(pharmacist);
		} else {
			logger.info("pharmacist is null");

			throw new ResourceNotFoundException("Pharmacist not found with ID " + id);

		}
	}

	public boolean resetPassword(Long id, String newPassword) {

		logger.info("updatePharmacist: " + id);
		Pharmacist pharmacist = getPharmacistById(id);

		if (pharmacist != null) {
			logger.info("pharmacist is not null");

			pharmacist.setPassword(passwordEncoder.encode(newPassword));
			pharmacist.setUpdatedAt(LocalDateTime.now());
			pharmacist.setUpdatedUserId(CommonUtil.getCurrentUserId()); // Retrieve from the current session

			pharmacistRepository.save(pharmacist);

			return true;
		} else {
			logger.info("pharmacist is null");

			throw new ResourceNotFoundException("Pharmacist not found with ID " + id);
		}
	}
}
