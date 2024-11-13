package com.hope.apiapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class NegotiationDto {

	private Long negotiationId;
	private Long jobId;
	private String jobRef;
	private LocalDate jobDate;
	private LocalTime jobStartTime;
	private LocalTime jobEndTime;
	private BigDecimal totalWorkHour;
	private String lunchArrangement;
	private String parkingOption;
	private BigDecimal ratePerMile;
	private BigDecimal originalHourlyRate;
	private BigDecimal originalTotalPaid;
	private BigDecimal purposedHourlyRate;
	private BigDecimal purposedTotalPaid;
	private BigDecimal counterHourlyRate;
	private BigDecimal counterTotalPaid;
	private String status;
	private String reason;
	private Long updatedUserId;
	private LocalDateTime updatedAt;
	private String branchName;
	private String branchAddress1;
	private String branchAddress2;
	private String branchPostalCode;
	private String pharmacistFirstName;
	private String pharmacistLastName;

	// Constructor
	public NegotiationDto() {
	}

	// Constructor matching the JPQL SELECT new clause
	public NegotiationDto(Long negotiationId, Long jobId, String jobRef, LocalDate jobDate, LocalTime jobStartTime,
			LocalTime jobEndTime, BigDecimal totalWorkHour, String lunchArrangement, String parkingOption,
			BigDecimal ratePerMile, BigDecimal originalHourlyRate, BigDecimal originalTotalPaid,
			BigDecimal purposedHourlyRate, BigDecimal purposedTotalPaid, BigDecimal counterHourlyRate,
			BigDecimal counterTotalPaid, String status, String reason, Long updatedUserId, LocalDateTime updatedAt,
			String branchName, String branchAddress1, String branchAddress2, String branchPostalCode,
			String pharmacistFirstName, String pharmacistLastName) {
		this.negotiationId = negotiationId;
		this.jobId = jobId;
		this.jobRef = jobRef;
		this.jobDate = jobDate;
		this.jobStartTime = jobStartTime;
		this.jobEndTime = jobEndTime;
		this.totalWorkHour = totalWorkHour;
		this.lunchArrangement = lunchArrangement;
		this.parkingOption = parkingOption;
		this.ratePerMile = ratePerMile;
		this.originalHourlyRate = originalHourlyRate;
		this.originalTotalPaid = originalTotalPaid;
		this.purposedHourlyRate = purposedHourlyRate;
		this.purposedTotalPaid = purposedTotalPaid;
		this.counterHourlyRate = counterHourlyRate;
		this.counterTotalPaid = counterTotalPaid;
		this.status = status;
		this.reason = reason;
		this.updatedUserId = updatedUserId;
		this.updatedAt = updatedAt;
		this.branchName = branchName;
		this.branchAddress1 = branchAddress1;
		this.branchAddress2 = branchAddress2;
		this.branchPostalCode = branchPostalCode;
		this.pharmacistFirstName = pharmacistFirstName;
		this.pharmacistLastName = pharmacistLastName;
	}

	public Long getNegotiationId() {
		return negotiationId;
	}

	public void setNegotiationId(Long negotiationId) {
		this.negotiationId = negotiationId;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getJobRef() {
		return jobRef;
	}

	public void setJobRef(String jobRef) {
		this.jobRef = jobRef;
	}

	public LocalDate getJobDate() {
		return jobDate;
	}

	public void setJobDate(LocalDate jobDate) {
		this.jobDate = jobDate;
	}

	public LocalTime getJobStartTime() {
		return jobStartTime;
	}

	public void setJobStartTime(LocalTime jobStartTime) {
		this.jobStartTime = jobStartTime;
	}

	public LocalTime getJobEndTime() {
		return jobEndTime;
	}

	public void setJobEndTime(LocalTime jobEndTime) {
		this.jobEndTime = jobEndTime;
	}

	public BigDecimal getTotalWorkHour() {
		return totalWorkHour;
	}

	public void setTotalWorkHour(BigDecimal totalWorkHour) {
		this.totalWorkHour = totalWorkHour;
	}

	public String getLunchArrangement() {
		return lunchArrangement;
	}

	public void setLunchArrangement(String lunchArrangement) {
		this.lunchArrangement = lunchArrangement;
	}

	public String getParkingOption() {
		return parkingOption;
	}

	public void setParkingOption(String parkingOption) {
		this.parkingOption = parkingOption;
	}

	public BigDecimal getRatePerMile() {
		return ratePerMile;
	}

	public void setRatePerMile(BigDecimal ratePerMile) {
		this.ratePerMile = ratePerMile;
	}

	public BigDecimal getOriginalHourlyRate() {
		return originalHourlyRate;
	}

	public void setOriginalHourlyRate(BigDecimal originalHourlyRate) {
		this.originalHourlyRate = originalHourlyRate;
	}

	public BigDecimal getOriginalTotalPaid() {
		return originalTotalPaid;
	}

	public void setOriginalTotalPaid(BigDecimal originalTotalPaid) {
		this.originalTotalPaid = originalTotalPaid;
	}

	public BigDecimal getPurposedHourlyRate() {
		return purposedHourlyRate;
	}

	public void setPurposedHourlyRate(BigDecimal purposedHourlyRate) {
		this.purposedHourlyRate = purposedHourlyRate;
	}

	public BigDecimal getPurposedTotalPaid() {
		return purposedTotalPaid;
	}

	public void setPurposedTotalPaid(BigDecimal purposedTotalPaid) {
		this.purposedTotalPaid = purposedTotalPaid;
	}

	public BigDecimal getCounterHourlyRate() {
		return counterHourlyRate;
	}

	public void setCounterHourlyRate(BigDecimal counterHourlyRate) {
		this.counterHourlyRate = counterHourlyRate;
	}

	public BigDecimal getCounterTotalPaid() {
		return counterTotalPaid;
	}

	public void setCounterTotalPaid(BigDecimal counterTotalPaid) {
		this.counterTotalPaid = counterTotalPaid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getUpdatedUserId() {
		return updatedUserId;
	}

	public void setUpdatedUserId(Long updatedUserId) {
		this.updatedUserId = updatedUserId;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchAddress1() {
		return branchAddress1;
	}

	public void setBranchAddress1(String branchAddress1) {
		this.branchAddress1 = branchAddress1;
	}

	public String getBranchAddress2() {
		return branchAddress2;
	}

	public void setBranchAddress2(String branchAddress2) {
		this.branchAddress2 = branchAddress2;
	}

	public String getBranchPostalCode() {
		return branchPostalCode;
	}

	public void setBranchPostalCode(String branchPostalCode) {
		this.branchPostalCode = branchPostalCode;
	}

	public String getPharmacistFirstName() {
		return pharmacistFirstName;
	}

	public void setPharmacistFirstName(String pharmacistFirstName) {
		this.pharmacistFirstName = pharmacistFirstName;
	}

	public String getPharmacistLastName() {
		return pharmacistLastName;
	}

	public void setPharmacistLastName(String pharmacistLastName) {
		this.pharmacistLastName = pharmacistLastName;
	}

}
