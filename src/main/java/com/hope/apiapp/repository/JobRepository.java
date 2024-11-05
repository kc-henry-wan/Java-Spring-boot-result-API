package com.hope.apiapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hope.apiapp.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

	Optional<Job> findById(Long JobId);

}
