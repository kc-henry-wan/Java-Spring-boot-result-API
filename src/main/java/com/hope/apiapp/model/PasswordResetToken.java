package com.hope.apiapp.model;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {

	private static final Logger logger = LoggerFactory.getLogger(PasswordResetToken.class);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "token_id")
	private Long tokenId;

	@Column(nullable = false)
	private String token;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "expiry_date", nullable = false)
	private Date expiryDate;

	@Column(name = "created_at", updatable = false, insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	// Constructors
	public PasswordResetToken() {
	}

	public PasswordResetToken(String token, Long userId, Date expiryDate) {
		this.token = token;
		this.userId = userId;
		this.expiryDate = expiryDate;
	}

	// Getter and Setter methods
	public Long getTokenId() {
		return tokenId;
	}

	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

}
