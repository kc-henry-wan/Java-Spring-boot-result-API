package com.hope.apiapp.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hope.apiapp.dto.JobDTO;
import com.hope.apiapp.dto.JobProjection;
import com.hope.apiapp.dto.JobRequestDto;
import com.hope.apiapp.dto.JobUpdateRequestDto;
import com.hope.apiapp.exception.ResourceConflictException;
import com.hope.apiapp.exception.ResourceNotFoundException;
import com.hope.apiapp.model.Job;
import com.hope.apiapp.repository.JobRepository;
import com.hope.apiapp.util.CommonUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Service
public class JobService {

	private static final Logger logger = LoggerFactory.getLogger(JobService.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private JobRepository jobRepository;

	@Transactional
	public List<JobDTO> findFilteredJobsWithLimitedFields(Double fromLat, Double fromLng, String fromDate,
			String toDate, String statusCode, String jobIds, String groupCode, Long pharmacistId, String sortingSeq) {
		logger.info("JobService - findFilteredJobsWithLimitedFields");

		StringBuilder queryBuilder = new StringBuilder(
				"SELECT j.jobId AS jobId, j.jobRef AS jobRef, j.jobDate AS jobDate, j.hourlyRate AS hourlyRate, "
						+ " j.totalWorkHour AS totalWorkHour, j.totalPaid AS totalPaid, "
						+ " j.lunchArrangement AS lunchArrangement, j.parkingOption AS parkingOption, "
						+ " j.ratePerMile AS ratePerMile, j.status AS status, "
						+ " pb.branchName AS branchName, pb.address1 AS branchAddress1, "
						+ " pb.address2 AS branchAddress2, pb.postalCode AS branchPostalCode, "
						+ " pb.latitude AS branchLatitude, pb.longitude AS branchLongitude, "
						+ " p.firstName AS pharmacistFirstName, p.lastName AS pharmacistLastName "
						+ " FROM Job j JOIN PharmacyBranch pb ON j.pharmacyBranchId = pb.pharmacyBranchId"
						+ " LEFT OUTER JOIN Pharmacist p ON j.pharmacistId = p.pharmacistId ");

		List<String> conditions = new ArrayList<>();

		if (fromDate != null) {
			conditions.add("j.jobDate >= :fromDate");
		}
		if (toDate != null) {
			conditions.add("j.jobDate <= :toDate");
		}
		if (statusCode != null) {
			conditions.add("j.status = :statusCode");
		}
		if (jobIds != null && !jobIds.isEmpty()) {
			conditions.add("j.jobId IN (:jobIds)");
		}
		if (groupCode != null) {
			conditions.add("j.pharmacyGroupId = :groupCode");
		}
		if (pharmacistId != null) {
			conditions.add("j.pharmacistId = :pharmacistId");
		}

		if (!conditions.isEmpty()) {
			queryBuilder.append(" WHERE ").append(String.join(" AND ", conditions));
		}

		logger.info("queryBuilder - finish where case. " + queryBuilder.toString());

		// Add dynamic ORDER BY clause based on sortingSeq
		if ("DA".equals(sortingSeq)) {
			queryBuilder.append(" ORDER BY j.jobDate");
		} else if ("HR".equals(sortingSeq)) {
			queryBuilder.append(" ORDER BY j.hourlyRate DESC");
		} else if ("TP".equals(sortingSeq)) {
			queryBuilder.append(" ORDER BY j.totalPaid DESC");
		} else {
			queryBuilder.append(" ORDER BY j.jobDate");
		}

		logger.info("queryBuilder - finish orderby case. " + queryBuilder.toString());

		Query query = entityManager.createQuery(queryBuilder.toString());
		logger.info("entityManager.createQuery executed");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		// Set parameters
		if (fromDate != null) {
			LocalDate lclFromDate = LocalDate.parse(fromDate, formatter);
			query.setParameter("fromDate", lclFromDate);
			logger.info("Set parameters finished: fromDate:" + lclFromDate);
		}
		if (toDate != null) {
			LocalDate lclToDate = LocalDate.parse(toDate, formatter);
			query.setParameter("toDate", lclToDate);
			logger.info("Set parameters finished: toDate:" + lclToDate);
		}
		if (statusCode != null) {
			query.setParameter("statusCode", statusCode);
			logger.info("Set parameters finished: statusCode:" + statusCode);
		}
		if (jobIds != null && !jobIds.isEmpty()) {
			List<Long> jobIdList = Arrays.stream(jobIds.split(",")).map(String::trim) // Remove any extra whitespace
					.map(Long::valueOf) // Convert to Long
					.collect(Collectors.toList());

			query.setParameter("jobIds", jobIdList);
			logger.info("Set parameters finished: jobIds:" + jobIdList);
		}
		if (groupCode != null) {
			query.setParameter("groupCode", groupCode);
			logger.info("Set parameters finished: groupCode:" + groupCode);
		}
		if (pharmacistId != null) {
			query.setParameter("pharmacistId", pharmacistId);
			logger.info("Set parameters finished: pharmacistId:" + pharmacistId);
		}

		List<Object[]> results = query.getResultList();
		logger.info("Retrieved results as List<Object[]>");

		List<JobDTO> jobDTOs = new ArrayList<>();
		logger.info("Initialized List<JobDTO>");

		// Iterate over results and populate JobDTO list
		for (Object[] row : results) {
			logger.debug("For each row: row[0]=" + row[0].toString());
			logger.debug("For each row: row[1]=" + row[1].toString());
			logger.debug("For each row: row[2]=" + row[2].toString());
			logger.debug("For each row: row[3]=" + row[3].toString());
			logger.debug("For each row: row[14]=" + row[14].toString());
			logger.debug("For each row: row[15]=" + row[15].toString());

			Double distance = 0.0;
			if (fromLat != null && fromLng != null) {
				distance = calculateDistance(fromLat, fromLng, (Double) row[14], (Double) row[15]);
				logger.info("For each row: distance:" + distance);
			}

			jobDTOs.add(new JobDTO( // Use DTO instead of projection class
					((Number) row[0]).longValue(), // jobId: Ensure it's cast to Number first, then to long
					(String) row[1], // jobRef: Cast to String
					(LocalDate) row[2], // jobDate: Convert java.sql.Date to LocalDate
					(BigDecimal) row[3], // hourlyRate,
					(BigDecimal) row[4], // totalWorkHour,
					(BigDecimal) row[5], // totalPaid,
					(String) row[6], // lunchArrangement,
					(String) row[7], // parkingOption,
					(BigDecimal) row[8], // ratePerMile,
					(String) row[9], // status
					(String) row[10], // branchName: Cast to String
					(String) row[11], // branchAddress1;
					(String) row[12], // branchAddress2;
					(String) row[13], // branchPostalCode;
					(Double) row[14], // branchLatitude;
					(Double) row[15], // branchLongitude;
					(String) row[16], // pharmacistFirstName;
					(String) row[17], // pharmacistLastName;
					(Double) distance // Distance between user address and branch address
			));
		}
		logger.info("List<JobDTO> load completed");

		if ("DI".equals(sortingSeq)) {
			Collections.sort(jobDTOs, Comparator.comparing(JobDTO::getDistance));
			logger.info("List<JobDTO> Sort by distance in ascending order ");
		}

		// Return the list of JobDTO objects
		return jobDTOs;
	}

	public JobProjection getJobByIdWithLimitedFields(Long id) {
		logger.info("findById: " + id);
		return jobRepository.getJobByIdWithLimitedFields(id)
				.orElseThrow(() -> new ResourceNotFoundException("Job not found"));
	}

	public Job findById(Long id) {
		logger.info("findById: " + id);
		return jobRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Job not found"));
	}

	public Job addJob(JobRequestDto jobRequest) {
		logger.info("addJob");

		Job job = new Job();

//TODO:add logic to gen ref number
//		job.setJobRef();
		job.setDescription(jobRequest.getDescription());
		job.setPharmacyGroupId(jobRequest.getPharmacyGroupId());

		// TODO:handle branchID
//		job.setPharmacyBranchId(jobRequest.getPharmacyBranchId());

		job.setJobDate(jobRequest.getJobDate());
		job.setJobStartTime(jobRequest.getJobStartTime());
		job.setJobEndTime(jobRequest.getJobEndTime());
		job.setHourlyRate(jobRequest.getHourlyRate());
		job.setTotalWorkHour(jobRequest.getTotalWorkHour());
		job.setTotalPaid(jobRequest.getTotalPaid());
		job.setLunchArrangement(jobRequest.getLunchArrangement());
		job.setParkingOption(jobRequest.getParkingOption());
		job.setRatePerMile(jobRequest.getRatePerMile());

		// Set default values for additional fields
		job.setStatus("Active"); // Default status
		job.setUpdatedUserId(CommonUtil.getCurrentUserId()); // Retrieve from the current session
		job.setCreatedAt(LocalDateTime.now()); // Set current time for creation
		job.setUpdatedAt(LocalDateTime.now()); // Set current time for update

		return jobRepository.save(job);
	}

	public Job updateJobStatus(Long id, JobUpdateRequestDto jobRequest) {

		logger.info("updateJobStatus: " + id);
		Job job = findById(id);

		if (job != null) {
			logger.info("job is not null");

			if (!jobRequest.getUpdatedAt().equals(job.getUpdatedAt())) {
				throw new ResourceConflictException("Record has been modified by another user.");
			}

			if ("Apply".equalsIgnoreCase(jobRequest.getStatus())) {
				job.setPharmacistId(CommonUtil.getCurrentUserId());
			} else if ("Withdraw".equalsIgnoreCase(jobRequest.getStatus())) {
				job.setPharmacistId(null);
			}
			job.setStatus(jobRequest.getStatus());
			job.setUpdatedAt(LocalDateTime.now()); // Set current time for update
			job.setUpdatedUserId(CommonUtil.getCurrentUserId()); // Retrieve from the current session

			return jobRepository.save(job);
		} else {
			logger.info("job is null");

			throw new ResourceNotFoundException("Job not found with ID " + id);

		}
	}

	public Job updateJob(Long id, JobRequestDto jobRequest) {

		logger.info("updateJob: " + id);
		Job job = findById(id);

		if (job != null) {
			logger.info("job is not null");

			if (!jobRequest.getUpdatedAt().equals(job.getUpdatedAt())) {
				throw new ResourceConflictException("Record has been modified by another user.");
			}

			job.setDescription(jobRequest.getDescription());
			job.setPharmacyGroupId(jobRequest.getPharmacyGroupId());
			job.setPharmacyBranchId(jobRequest.getPharmacyBranchId());
			job.setJobDate(jobRequest.getJobDate());
			job.setJobStartTime(jobRequest.getJobStartTime());
			job.setJobEndTime(jobRequest.getJobEndTime());
			job.setHourlyRate(jobRequest.getHourlyRate());
			job.setTotalWorkHour(jobRequest.getTotalWorkHour());
			job.setTotalPaid(jobRequest.getTotalPaid());
			job.setLunchArrangement(jobRequest.getLunchArrangement());
			job.setParkingOption(jobRequest.getParkingOption());
			job.setRatePerMile(jobRequest.getRatePerMile());

			job.setUpdatedAt(LocalDateTime.now()); // Set current time for update
			job.setUpdatedUserId(CommonUtil.getCurrentUserId()); // Retrieve from the current session

			return jobRepository.save(job);
		} else {
			logger.info("job is null");

			throw new ResourceNotFoundException("Job not found with ID " + id);

		}
	}

	// Haversine formula for distance calculation
	private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		double earthRadius = 6371; // km
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lon2 - lon1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return earthRadius * c;
	}
}
