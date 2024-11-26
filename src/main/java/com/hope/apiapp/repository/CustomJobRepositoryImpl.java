package com.hope.apiapp.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.hope.apiapp.dto.JobDto;
import com.hope.apiapp.util.CommonUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

//Repository Layer
@Repository
public class CustomJobRepositoryImpl implements CustomJobRepository {

	private static final Logger logger = LoggerFactory.getLogger(CustomJobRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	public Page<JobDto> findFilteredJobsWithLimitedFields(Pageable pageable, Double fromLat, Double fromLng,
			String fromDate, String toDate, String statusCode, String jobIds, String groupCode, Long pharmacistId,
			String orderBy) {

		StringBuilder jpql = new StringBuilder("SELECT j.jobId AS jobId, j.jobRef AS jobRef, j.jobDate AS jobDate, "
				+ " j.jobStartTime AS jobStartTime, j.jobEndTime AS jobEndTime , j.hourlyRate AS hourlyRate, "
				+ " j.totalWorkHour AS totalWorkHour, j.totalPaid AS totalPaid, "
				+ " j.lunchArrangement AS lunchArrangement, j.parkingOption AS parkingOption, "
				+ " j.ratePerMile AS ratePerMile, j.status AS status, "
				+ " pb.branchName AS branchName, pb.address1 AS branchAddress1, "
				+ " pb.address2 AS branchAddress2, pb.postalCode AS branchPostalCode, "
				+ " pb.latitude AS branchLatitude, pb.longitude AS branchLongitude, "
				+ " p.firstName AS pharmacistFirstName, p.lastName AS pharmacistLastName "
				+ " FROM Job j JOIN PharmacyBranch pb ON j.pharmacyBranchId = pb.pharmacyBranchId"
				+ " LEFT OUTER JOIN Pharmacist p ON j.pharmacistId = p.pharmacistId " + " WHERE j.deleted = false ");

		StringBuilder countJpql = new StringBuilder("SELECT COUNT(j) FROM Job j WHERE j.deleted = false ");

		List<String> conditions = new ArrayList<>();

		if (fromDate != null) {
			conditions.add("j.jobDate >= :fromDate");
		}
		if (toDate != null) {
			conditions.add("j.jobDate <= :toDate");
		}
		if (statusCode != null) {
			conditions.add("LOWER(j.status) = LOWER(:statusCode)");
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
			jpql.append(" AND ").append(String.join(" AND ", conditions));
			countJpql.append(" AND ").append(String.join(" AND ", conditions));
		}

		logger.info("queryBuilder - finish where case. " + jpql.toString());
		logger.info("queryBuilder - finish where case. " + countJpql.toString());

		// Add order by
		if ("DA".equalsIgnoreCase(orderBy)) {
			jpql.append(" ORDER BY j.jobDate, j.jobStartTime ");
		} else if ("HR".equalsIgnoreCase(orderBy)) {
			jpql.append(" ORDER BY j.hourlyRate DESC, j.jobDate, j.jobStartTime ");
		} else if ("TP".equalsIgnoreCase(orderBy)) {
			jpql.append(" ORDER BY j.totalPaid DESC, j.jobDate, j.jobStartTime ");
		} else {
			jpql.append(" ORDER BY j.jobDate, j.jobStartTime, j.hourlyRate DESC ");
		}

		logger.info("queryBuilder - finish order by:" + jpql.toString());

		Query query = entityManager.createQuery(jpql.toString());
		TypedQuery<Long> countQuery = entityManager.createQuery(countJpql.toString(), Long.class);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		// Set parameters
		if (fromDate != null) {
			LocalDate lclFromDate = LocalDate.parse(fromDate, formatter);
			query.setParameter("fromDate", lclFromDate);
			countQuery.setParameter("fromDate", lclFromDate);
		}
		if (toDate != null) {
			LocalDate lclToDate = LocalDate.parse(toDate, formatter);
			query.setParameter("toDate", lclToDate);
			countQuery.setParameter("toDate", lclToDate);
		}
		if (statusCode != null) {
			query.setParameter("statusCode", statusCode);
			countQuery.setParameter("statusCode", statusCode);
		}
		if (jobIds != null && !jobIds.isEmpty()) {
			List<Long> jobIdList = Arrays.stream(jobIds.split(",")).map(String::trim) // Remove any extra whitespace
					.map(Long::valueOf) // Convert to Long
					.collect(Collectors.toList());

			query.setParameter("jobIds", jobIdList);
			countQuery.setParameter("jobIds", jobIdList);
		}
		if (groupCode != null) {
			query.setParameter("groupCode", groupCode);
			countQuery.setParameter("groupCode", groupCode);
		}
		if (pharmacistId != null) {
			query.setParameter("pharmacistId", pharmacistId);
			countQuery.setParameter("pharmacistId", pharmacistId);
		}

//		// Setting pagination using Pageable
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		List<Object[]> results = query.getResultList();

		List<JobDto> resultList = new ArrayList<>();

		// Iterate over results and populate JobDTO list
		for (Object[] row : results) {
			Double distance = 0.0;
			if (fromLat != null && fromLng != null) {
				distance = CommonUtil.calculateDistance(fromLat, fromLng, (Double) row[16], (Double) row[17]);
			}

			resultList.add(new JobDto( // Use DTO instead of projection class
					((Number) row[0]).longValue(), // jobId: Ensure it's cast to Number first, then to long
					(String) row[1], // jobRef: Cast to String
					(LocalDate) row[2], // jobDate: Convert java.sql.Date to LocalDate
					(LocalTime) row[3], // jobDate: Convert java.sql.Time to LocalTime
					(LocalTime) row[4], // jobDate: Convert java.sql.Time to LocalTime
					(BigDecimal) row[5], // hourlyRate,
					(BigDecimal) row[6], // totalWorkHour,
					(BigDecimal) row[7], // totalPaid,
					(String) row[8], // lunchArrangement,
					(String) row[9], // parkingOption,
					(BigDecimal) row[10], // ratePerMile,
					(String) row[11], // status
					(String) row[12], // branchName: Cast to String
					(String) row[13], // branchAddress1;
					(String) row[14], // branchAddress2;
					(String) row[15], // branchPostalCode;
					(Double) row[16], // branchLatitude;
					(Double) row[17], // branchLongitude;
					(String) row[18], // pharmacistFirstName;
					(String) row[19], // pharmacistLastName;
					(Double) distance // Distance between user address and branch address
			));
		}
		logger.info("List<JobDto> load completed");

		if ("DI".equalsIgnoreCase(orderBy)) {
			resultList.sort(Comparator.comparing(JobDto::getDistance));
		}

		// Getting total count for pagination
		Long totalElements = countQuery.getSingleResult();

		return new PageImpl<>(resultList, pageable, totalElements);

	}
}
