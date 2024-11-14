package com.hope.apiapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hope.apiapp.dto.PharmacistDocProjection;
import com.hope.apiapp.model.PharmacistDoc;

@Repository
public interface PharmacistDocRepository extends JpaRepository<PharmacistDoc, Integer> {

	@Query("SELECT i.imageId AS imageId, i.imageType AS imageType FROM PharmacistDoc i where i.pharmacistId = :pharmacistId")
	List<PharmacistDocProjection> findByPharmacistId(@Param("pharmacistId") Long pharmacistId);

	Optional<PharmacistDoc> findByImageId(Long imageId);
}