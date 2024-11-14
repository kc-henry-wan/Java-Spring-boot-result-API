package com.hope.apiapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hope.apiapp.dto.NegotiationProjection;
import com.hope.apiapp.model.Negotiation;

@Repository
public interface NegotiationRepository extends JpaRepository<Negotiation, Long>, CustomNegotiationRepository {

	@Query("SELECT n.negotiationId AS negotiationId, j.jobId AS jobId, j.jobRef AS jobRef, j.jobDate AS jobDate, "
			+ " j.jobStartTime AS jobStartTime, j.jobEndTime AS jobEndTime, j.totalWorkHour AS totalWorkHour, "
			+ " j.lunchArrangement AS lunchArrangement, j.parkingOption AS parkingOption, j.ratePerMile AS ratePerMile, "
			+ " n.originalHourlyRate AS originalHourlyRate, n.originalTotalPaid AS originalTotalPaid, "
			+ " n.purposedHourlyRate AS purposedHourlyRate, n.purposedTotalPaid AS purposedTotalPaid, "
			+ " n.counterHourlyRate AS counterHourlyRate, n.counterTotalPaid AS counterTotalPaid, "
			+ " n.status AS status, n.reason AS reason, n.updatedUserId AS updatedUserId, n.updatedAt AS updatedAt, "
			+ " pb.branchName AS branchName, pb.address1 AS branchAddress1, "
			+ " pb.address2 AS branchAddress2, pb.postalCode AS branchPostalCode, "
			+ " p.firstName AS pharmacistFirstName, p.lastName AS pharmacistLastName "
			+ " FROM Negotiation n JOIN Job j on n.jobId = j.jobId "
			+ " JOIN PharmacyBranch pb ON j.pharmacyBranchId = pb.pharmacyBranchId "
			+ " LEFT OUTER JOIN Pharmacist p ON n.pharmacistId = p.pharmacistId "
			+ " WHERE n.negotiationId = :negotiationId ")
	Optional<NegotiationProjection> getNegotiationById(@Param("negotiationId") Long pharmacistId);

	Optional<Negotiation> findById(Long pharmacistId);

	List<Negotiation> findByJobId(Long jobId);
}
