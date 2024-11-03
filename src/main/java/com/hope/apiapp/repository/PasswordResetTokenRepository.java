package com.hope.apiapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hope.apiapp.model.PasswordResetToken;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
	// Custom queries can be added here if necessary
	Optional<PasswordResetToken> findByToken(String token);
}
