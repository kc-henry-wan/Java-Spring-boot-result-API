package com.hope.apiapp.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.hope.apiapp.dto.NegotiationDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

//Repository Layer
@Repository
public class CustomNegotiationRepositoryImpl implements CustomNegotiationRepository {

	private static final Logger logger = LoggerFactory.getLogger(CustomNegotiationRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	public Page<NegotiationDto> getNegotiations(Pageable pageable, String status, Long pharmacistId) {

		StringBuilder jpql = new StringBuilder("SELECT new com.hope.apiapp.dto.NegotiationDto("
				+ " n.negotiationId AS negotiationId, j.jobId AS jobId, j.jobRef AS jobRef, j.jobDate AS jobDate, "
				+ " j.jobStartTime AS jobStartTime, " + " j.jobEndTime AS jobEndTime, "
				+ " j.totalWorkHour AS totalWorkHour, "
				+ " j.lunchArrangement AS lunchArrangement, j.parkingOption AS parkingOption, j.ratePerMile AS ratePerMile, "
				+ " n.originalHourlyRate AS originalHourlyRate, n.originalTotalPaid AS originalTotalPaid, "
				+ " n.purposedHourlyRate AS purposedHourlyRate, n.purposedTotalPaid AS purposedTotalPaid, "
				+ " n.counterHourlyRate AS counterHourlyRate, n.counterTotalPaid AS counterTotalPaid, "
				+ " n.status AS status, n.reason AS reason, n.updatedUserId AS updatedUserId, n.updatedAt AS updatedAt, "
				+ " pb.branchName AS branchName, pb.address1 AS branchAddress1, "
				+ " pb.address2 AS branchAddress2, pb.postalCode AS branchPostalCode, "
				+ " p.firstName AS pharmacistFirstName, p.lastName AS pharmacistLastName) "
				+ " FROM Negotiation n JOIN Job j on n.jobId = j.jobId "
				+ " JOIN PharmacyBranch pb ON j.pharmacyBranchId = pb.pharmacyBranchId "
				+ " JOIN Pharmacist p ON n.pharmacistId = p.pharmacistId ");

		StringBuilder countJpql = new StringBuilder("SELECT COUNT(n) FROM Negotiation n ");

		List<String> conditions = new ArrayList<>();

		if (status != null && !status.equals("")) {
			conditions.add("LOWER(n.status) = LOWER(:status)");
		}

		if (pharmacistId != null) {
			conditions.add("n.pharmacistId = :pharmacistId");
		}

		if (!conditions.isEmpty()) {
			jpql.append(" WHERE ").append(String.join(" AND ", conditions));
			countJpql.append(" WHERE ").append(String.join(" AND ", conditions));
		}

		logger.info("queryBuilder - finish where case. " + jpql.toString());
		logger.info("queryBuilder - finish where case. " + countJpql.toString());

		TypedQuery<NegotiationDto> query = entityManager.createQuery(jpql.toString(), NegotiationDto.class);
		TypedQuery<Long> countQuery = entityManager.createQuery(countJpql.toString(), Long.class);

		// Set parameters
		if (status != null && !status.equals("")) {
			query.setParameter("status", status);
			countQuery.setParameter("status", status);
		}
		if (pharmacistId != null) {
			query.setParameter("pharmacistId", pharmacistId);
			countQuery.setParameter("pharmacistId", pharmacistId);
		}

//		// Setting pagination using Pageable
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		List<NegotiationDto> resultList = query.getResultList();

		logger.info("List<NegotiationDto> load completed");

		// Getting total count for pagination
		Long totalElements = countQuery.getSingleResult();

		return new PageImpl<>(resultList, pageable, totalElements);

	}
}
