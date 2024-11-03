package com.hope.apiapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hope.apiapp.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

//	@Query(value = "SELECT " + "j.job_id AS JobId," + "j.job_ref AS JobRef," + "j.job_date AS JobDate,"
//			+ "j.hourly_rate AS HourlyRate," + "j.total_paid AS TotalPaid," + "j.total_work_hour AS TotalWorkHour,"
//			+ "j.lunch_arrangement AS LunchArrangement," + "j.parking_option AS ParkingOption,"
//			+ "j.rate_per_mile AS RatePerMile," + "j.status AS Status," + "j.pharmacy_group_id AS GroupCode,"
//			+ "pb.branch_name AS BranchName," + "pb.address_1 AS BranchAddress1," + "pb.address_2 AS BranchAddress2,"
//			+ "pb.postal_code AS BranchPostalCode," + "pb.latitude AS BranchLatitude,"
//			+ "pb.longitude AS BranchLongitude" + "FROM tbl_jobs j, " + "tbl_pharmacy_branch pb"
//			+ "WHERE pb.pharmacy_branch_id = j.pharmacy_branch_id", nativeQuery = true)
//
//	@Query("SELECT  j.jobId AS jobId, j.jobRef AS jobRef, j.jobDate AS jobDate, pb.branchName AS branchName FROM Job j JOIN pharmacyBranch pb "
//			+ "ORDER BY " + "CASE " + "WHEN :sortingSeq = 'HR' THEN j.hourlyRate "
//			+ "WHEN :sortingSeq = 'TP' THEN j.totalPaid " + "ELSE j.jobDate " + "END DESC")
//	List<JobProjection> findFilteredJobsWithLimitedFields(@Param("sortingSeq") String sortingSeq);
//	List<JobProjection> findFilteredJobsWithLimitedFields(@Param("fromDate") String fromDate,
//			@Param("toDate") String toDate, @Param("statusCode") String statusCode, @Param("jobIds") String jobIds,
//			@Param("groupCode") String groupCode, @Param("sortingSeq") String sortingSeq);

	Optional<Job> findById(Long JobId);

}
