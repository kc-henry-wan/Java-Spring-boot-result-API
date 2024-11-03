package com.hope.apiapp.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_pharmacy_group")
public class PharmacyGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pharmacy_group_id")
	private Long pharmacyGroupId;

	@Column(name = "group_name", nullable = false)
	private String groupName;

	@Column(name = "group_code", nullable = false, length = 3)
	private String groupCode;

	@Column(name = "status")
	private String status;

	@Column(name = "updated_user_id")
	private Long updatedUserId;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	// Constructors
	public PharmacyGroup() {
		// Default constructor
	}

	public PharmacyGroup(Long pharmacyGroupId, String groupName, String groupCode, String status, Long updatedUserId,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.pharmacyGroupId = pharmacyGroupId;
		this.groupName = groupName;
		this.groupCode = groupCode;
		this.status = status;
		this.updatedUserId = updatedUserId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// Getters and Setters
	public Long getPharmacyGroupId() {
		return pharmacyGroupId;
	}

	public void setPharmacyGroupId(Long pharmacyGroupId) {
		this.pharmacyGroupId = pharmacyGroupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
}
