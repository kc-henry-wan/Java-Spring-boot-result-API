package com.hope.apiapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hope.apiapp.model.PharmacyBranch;

@Repository
public interface PharmacyBranchRepository extends JpaRepository<PharmacyBranch, Long> {
	Page<PharmacyBranch> findByStatus(String status, Pageable pageable);

	Page<PharmacyBranch> findAll(Pageable pageable);
}
