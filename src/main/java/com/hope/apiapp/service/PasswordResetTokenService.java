package com.hope.apiapp.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hope.apiapp.model.PasswordResetToken;
import com.hope.apiapp.model.Pharmacist;
import com.hope.apiapp.repository.PasswordResetTokenRepository;
import com.hope.apiapp.repository.PharmacistRepository;

@Service
public class PasswordResetTokenService {

	private static final Logger logger = LoggerFactory.getLogger(PasswordResetTokenService.class);

	private static final int EXPIRATION = 24 * 60; // Token validity duration: 24 hours

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	private PharmacistRepository pharmacistRepository;

	private EmailService emailService;

	public String generateResetToken(String email) {
		Pharmacist pUser = null;

		pUser = pharmacistRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Pharmacist not found"));

		String token = UUID.randomUUID().toString();
		logger.info("PasswordResetService - generateResetToken: " + token);

		PasswordResetToken resetToken = new PasswordResetToken();
		resetToken.setToken(token);
		resetToken.setUserId(pUser.getPharmacistId());
		resetToken.setExpiryDate(calculateExpiryDate(EXPIRATION));

		logger.info("calculateExpiryDate resetToken expiry date:" + resetToken.getExpiryDate());

		passwordResetTokenRepository.save(resetToken);

		// TODO:Logic to send the token to the user's email
//		emailService.sendPasswordResetEmail(email, token);

		return "Reset token sent to email.";
	}

	public PasswordResetToken validatePasswordResetToken(String token) {

		logger.info("validatePasswordResetToken start");

		PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
				.orElseThrow(() -> new RuntimeException("Invalid token"));

		logger.info("validatePasswordResetToken expiryDateTime:" + resetToken.getExpiryDate());

		LocalDateTime expiryDateTime = resetToken.getExpiryDate().toInstant().atZone(ZoneId.systemDefault())
				.toLocalDateTime();

		logger.info("validatePasswordResetToken expiryDateTime:" + expiryDateTime);

		if (expiryDateTime.isBefore(LocalDateTime.now())) {
			throw new RuntimeException("Token expired");
		}
		return resetToken;
	}

	public void invalidateToken(PasswordResetToken token) {
		passwordResetTokenRepository.delete(token);
	}

	// Utility method to calculate the expiry date
	private Date calculateExpiryDate(int expiryTimeInMinutes) {

		logger.info("calculateExpiryDate start");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		logger.info("calculateExpiryDate current time: " + calendar.getTime());

		calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
		logger.info("calculateExpiryDate expiry time: " + calendar.getTime());

		return calendar.getTime();
	}

//	// Method to check if the token is expired
//	public boolean isExpired() {
//		return new Date().after(this.expiryDate);
//	}

}
