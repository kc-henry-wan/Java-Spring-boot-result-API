package com.hope.apiapp.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_jobs")

public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long jobId;

	@Column(unique = true, nullable = false)
	private String jobRef;

	@Column(columnDefinition = "TEXT")
	private String description;

	private Long pharmacistId;
	private Long pharmacyGroupId;

	private Long pharmacyBranchId;
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "pharmacy_branch_id", foreignKey = @ForeignKey(name = "fk_pharmacy_branch"))
//	private PharmacyBranch pharmacyBranch;

	@Column(nullable = false)
	private LocalDate jobDate;

	@Column(nullable = false)
	private LocalTime jobStartTime;

	@Column(nullable = false)
	private LocalTime jobEndTime;

	private BigDecimal hourlyRate;
	private BigDecimal totalWorkHour;
	private BigDecimal totalPaid;
	private String lunchArrangement;
	private String parkingOption;
	private BigDecimal ratePerMile;
	private String status;
	private Boolean deleted = false;

	private Long updatedUserId;

	private LocalDateTime createdAt = LocalDateTime.now();

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public BigDecimal getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(BigDecimal hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public BigDecimal getTotalWorkHour() {
		return totalWorkHour;
	}

	public void setTotalWorkHour(BigDecimal totalWorkHour) {
		this.totalWorkHour = totalWorkHour;
	}

	public BigDecimal getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(BigDecimal totalPaid) {
		this.totalPaid = totalPaid;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Long getUpdatedUserId() {
		return updatedUserId;
	}

	public void setUpdatedUserId(Long updatedUserId) {
		this.updatedUserId = updatedUserId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getPharmacistId() {
		return pharmacistId;
	}

	public void setPharmacistId(Long pharmacistId) {
		this.pharmacistId = pharmacistId;
	}

	public Long getPharmacyGroupId() {
		return pharmacyGroupId;
	}

	public void setPharmacyGroupId(Long pharmacyGroupId) {
		this.pharmacyGroupId = pharmacyGroupId;
	}

//	public PharmacyBranch getPharmacyBranch() {
//		return pharmacyBranch;
//	}
//
//	public void setPharmacyBranch(PharmacyBranch pharmacyBranch) {
//		this.pharmacyBranch = pharmacyBranch;
//	}

	public Long getPharmacyBranchId() {
		return pharmacyBranchId;
	}

	public void setPharmacyBranchId(Long pharmacyBranchId) {
		this.pharmacyBranchId = pharmacyBranchId;
	}

}
