package com.hope.apiapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class JobDto {
	private Long jobId;
	private String jobRef;
	private LocalDate jobDate;
	private LocalTime jobStartTime;
	private LocalTime jobEndTime;
	private BigDecimal hourlyRate;
	private BigDecimal totalWorkHour;
	private BigDecimal totalPaid;
	private String lunchArrangement;
	private String parkingOption;
	private BigDecimal ratePerMile;
	private String status;
	private String branchName;
	private String branchAddress1;
	private String branchAddress2;
	private String branchPostalCode;
	private Double branchLatitude;
	private Double branchLongitude;
	private String pharmacistFirstName;
	private String pharmacistLastName;
	private Double distance;

	// Constructor
	public JobDto() {
	}

	public JobDto(Long jobId, String jobRef, LocalDate jobDate, LocalTime jobStartTime, LocalTime jobEndTime,
			BigDecimal hourlyRate, BigDecimal totalWorkHour, BigDecimal totalPaid, String lunchArrangement,
			String parkingOption, BigDecimal ratePerMile, String status, String branchName, String branchAddress1,
			String branchAddress2, String branchPostalCode, Double branchLatitude, Double branchLongitude,
			String pharmacistFirstName, String pharmacistLastName, Double distance) {
		this.jobId = jobId;
		this.jobRef = jobRef;
		this.jobDate = jobDate;
		this.jobStartTime = jobStartTime;
		this.jobEndTime = jobEndTime;
		this.hourlyRate = hourlyRate;
		this.totalWorkHour = totalWorkHour;
		this.totalPaid = totalPaid;
		this.lunchArrangement = lunchArrangement;
		this.parkingOption = parkingOption;
		this.ratePerMile = ratePerMile;
		this.status = status;
		this.branchName = branchName;
		this.branchAddress1 = branchAddress1;
		this.branchAddress2 = branchAddress2;
		this.branchPostalCode = branchPostalCode;
		this.branchLatitude = branchLatitude;
		this.branchLongitude = branchLongitude;
		this.pharmacistFirstName = pharmacistFirstName;
		this.pharmacistLastName = pharmacistLastName;
		this.distance = distance;
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

	public Double getBranchLatitude() {
		return branchLatitude;
	}

	public void setBranchLatitude(Double branchLatitude) {
		this.branchLatitude = branchLatitude;
	}

	public Double getBranchLongitude() {
		return branchLongitude;
	}

	public void setBranchLongitude(Double branchLongitude) {
		this.branchLongitude = branchLongitude;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
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
