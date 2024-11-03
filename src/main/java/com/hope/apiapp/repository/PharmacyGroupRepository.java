package com.hope.apiapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hope.apiapp.model.PharmacyGroup;

@Repository
public interface PharmacyGroupRepository extends JpaRepository<PharmacyGroup, Long> {
}
