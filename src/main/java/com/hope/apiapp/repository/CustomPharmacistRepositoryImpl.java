package com.hope.apiapp.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.hope.apiapp.dto.PharmacistDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

//Repository Layer
@Repository
public class CustomPharmacistRepositoryImpl implements CustomPharmacistRepository {

	private static final Logger logger = LoggerFactory.getLogger(CustomPharmacistRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	public Page<PharmacistDto> searchPharmacists(Pageable pageable, String term, String status) {

		StringBuilder jpql = new StringBuilder(
				"SELECT p.pharmacistId AS pharmacistId, p.firstName AS firstName, p.lastName AS lastName, "
						+ " p.mobile AS mobile, p.email AS email, p.address1 AS address1, p.address2 AS address2, "
						+ " p.postalCode AS postalCode, p.status AS status FROM Pharmacist p ");

		StringBuilder countJpql = new StringBuilder("SELECT COUNT(p) FROM Pharmacist p ");

		List<String> conditions = new ArrayList<>();

		if (status != null && !status.equals("")) {
			conditions.add("LOWER(p.status) = LOWER(:status)");
		}

		if (term != null && !term.equals("")) {
			conditions.add("(LOWER(p.firstName) LIKE LOWER(CONCAT('%', :term, '%')) "
					+ "OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :term, '%')) "
					+ "OR LOWER(p.email) LIKE LOWER(CONCAT('%', :term, '%')))");
		}

		if (!conditions.isEmpty()) {
			jpql.append(" WHERE ").append(String.join(" AND ", conditions));
			countJpql.append(" WHERE ").append(String.join(" AND ", conditions));
		}

		logger.info("queryBuilder - finish where case. " + jpql.toString());
		logger.info("queryBuilder - finish where case. " + countJpql.toString());

		TypedQuery<PharmacistDto> query = entityManager.createQuery(jpql.toString(), PharmacistDto.class);
		TypedQuery<Long> countQuery = entityManager.createQuery(countJpql.toString(), Long.class);

		// Set parameters
		if (status != null && !status.equals("")) {
			query.setParameter("status", status);
			countQuery.setParameter("status", status);
		}
		if (term != null && !term.equals("")) {
			query.setParameter("term", term);
			countQuery.setParameter("term", term);
		}

//		// Setting pagination using Pageable
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		List<PharmacistDto> resultList = query.getResultList();

		logger.info("List<PharmacistDto> load completed");

		// Getting total count for pagination
		Long totalElements = countQuery.getSingleResult();

		return new PageImpl<>(resultList, pageable, totalElements);

	}
}
