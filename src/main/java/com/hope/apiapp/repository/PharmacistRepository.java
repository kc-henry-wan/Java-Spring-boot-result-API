package com.hope.apiapp.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hope.apiapp.dto.PharmacistProjection;
import com.hope.apiapp.model.Pharmacist;

public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {
	@Query("SELECT p.pharmacistId AS pharmacistId, p.firstName AS firstName, p.lastName AS lastName, p.mobile AS mobile, p.address1 AS address1, p.address2 AS address2, p.postalCode AS postalCode, p.status AS status FROM Pharmacist p  WHERE :status IS NULL OR p.status = :status ")
	Page<PharmacistProjection> findByStatusWithLimitedFields(@Param("status") String status, Pageable pageable);

	@Query("SELECT p.pharmacistId AS pharmacistId, p.firstName AS firstName, p.lastName AS lastName, p.mobile AS mobile, p.address1 AS address1, p.address2 AS address2, p.postalCode AS postalCode, p.status AS status, p.updatedAt AS updatedAt FROM Pharmacist p WHERE p.pharmacistId = ?1")
	Optional<PharmacistProjection> findByIdWithLimitedFields(Long id);

	Optional<Pharmacist> findById(Long pharmacistId);

	Optional<Pharmacist> findByEmail(String email);
}