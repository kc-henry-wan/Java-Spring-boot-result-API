package com.hope.apiapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hope.apiapp.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

	@Query("SELECT j.jobId AS jobId, j.jobRef AS jobRef, j.jobDate AS jobDate, j.hourlyRate AS hourlyRate, "
						+ "j.totalWorkHour AS totalWorkHour, j.totalPaid AS totalPaid, "
						+ "j.lunchArrangement AS lunchArrangement, j.parkingOption AS parkingOption, "
						+ "j.ratePerMile AS ratePerMile, j.status AS status, "
						+ "pb.branchName AS branchName, pb.address1 AS branchAddress1, "
						+ "pb.address2 AS branchAddress2, pb.postalCode AS branchPostalCode, "
						+ "pb.latitude AS branchLatitude, pb.longitude AS branchLongitude "
						+ "FROM Job j JOIN j.pharmacyBranch pb "
	      + "WHERE j.jobId = :jobId ")
	List<JobDTO> getJobByIdWithLimitedFields(@Param("jobId") Long jobId);
	
	Optional<Job> findById(Long JobId);

}
