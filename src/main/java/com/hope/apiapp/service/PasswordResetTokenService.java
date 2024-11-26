package com.hope.apiapp.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hope.apiapp.exception.ResourceNotFoundException;
import com.hope.apiapp.model.PasswordResetToken;
import com.hope.apiapp.model.Pharmacist;
import com.hope.apiapp.repository.PasswordResetTokenRepository;
import com.hope.apiapp.repository.PharmacistRepository;

@Service
public class PasswordResetTokenService {

	private static final Logger logger = LoggerFactory.getLogger(PasswordResetTokenService.class);

	private static final int EXPIRATION_30DAYS = 30 * 24 * 60; // Token validity duration: 30 days

	private static final int EXPIRATION_24HOURS = 24 * 60; // Token validity duration: 24 hours

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	private PharmacistRepository pharmacistRepository;

//	private EmailService emailService;

	public String generateResetToken(String email, Boolean forActivateNewUser) {
		Pharmacist pUser = null;

		pUser = pharmacistRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("RNF-R201"));

		String token = UUID.randomUUID().toString();

		PasswordResetToken resetToken = new PasswordResetToken();
		resetToken.setToken(token);
		resetToken.setUserId(pUser.getPharmacistId());

		if (forActivateNewUser) {
			resetToken.setExpiryDate(calculateExpiryDate(EXPIRATION_30DAYS));
		} else {
			resetToken.setExpiryDate(calculateExpiryDate(EXPIRATION_24HOURS));
		}

		passwordResetTokenRepository.save(resetToken);

		// TODO:Logic to send the token to the user's email
		if (forActivateNewUser) {
			logger.info("emailService.sendActivateUserEmail: " + token);
			// TODO: Build email service
//			emailService.sendActivateUserEmail(email, token);
		} else {
			logger.info("emailService.sendPasswordResetEmail: " + token);
//			emailService.sendPasswordResetEmail(email, token);
		}

		return "Reset token sent to email.";
	}

	public PasswordResetToken validatePasswordResetToken(String token) {

		PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
				.orElseThrow(() -> new ResourceNotFoundException("RNF-R202"));

		LocalDateTime expiryDateTime = resetToken.getExpiryDate();

		if (expiryDateTime.isBefore(LocalDateTime.now())) {
			throw new ResourceNotFoundException("RNF-R203-EXPIRED");
		}
		return resetToken;
	}

	public void invalidateToken(PasswordResetToken token) {
		passwordResetTokenRepository.delete(token);
	}

	// Utility method to calculate the expiry date
	private LocalDateTime calculateExpiryDate(int expiryTimeInMinutes) {
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime expiryTime = currentTime.plusMinutes(expiryTimeInMinutes);

		return expiryTime;
	}
}
