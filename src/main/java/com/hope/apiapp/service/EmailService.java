package com.hope.apiapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	public void sendPasswordResetEmail(String email, String token) {
		// TODO:This should be connected to an SMTP server or email API
		logger.warn("Password reset token sent to " + email + ": " + token);
	}
}
