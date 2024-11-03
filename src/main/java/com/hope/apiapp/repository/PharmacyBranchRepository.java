package com.hope.apiapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hope.apiapp.model.PharmacyBranch;

@Repository
public interface PharmacyBranchRepository extends JpaRepository<PharmacyBranch, Long> {
}
