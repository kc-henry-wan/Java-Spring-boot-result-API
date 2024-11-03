package com.hope.apiapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hope.apiapp.dto.PharmacistProjection;
import com.hope.apiapp.model.Pharmacist;

public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {
	@Query("SELECT p.firstName AS firstName, p.lastName AS lastName, p.mobile AS mobile, p.address1 AS address1, p.address2 AS address2, p.postalCode AS postalCode, p.status AS status FROM Pharmacist p")
//	@Query(value = "SELECT first_name AS firstName, last_name AS lastName, address_1 AS address1, address_2 AS address2 FROM tbl_pharmacist", nativeQuery = true)
	List<PharmacistProjection> findAllPharmacistsWithLimitedFields();

	@Query("SELECT p.firstName AS firstName, p.lastName AS lastName, p.mobile AS mobile, p.address1 AS address1, p.address2 AS address2, p.postalCode AS postalCode, p.status AS status FROM Pharmacist p WHERE p.pharmacistId = ?1")
	Optional<PharmacistProjection> findPharmacistByIdWithLimitedFields(Long id);

	Optional<Pharmacist> findById(Long pharmacistId);

	Optional<Pharmacist> findByEmail(String email);
}