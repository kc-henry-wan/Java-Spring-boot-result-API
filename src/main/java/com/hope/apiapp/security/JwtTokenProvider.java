package com.hope.apiapp.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24 hours

	@Value("${jwt.secret}")
	private String secretKey;

	public String generateToken(String username) {

		logger.info("generateToken: start");

//		Map<String, Object> claims = new HashMap<>();

//		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//				.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
		return Jwts.builder().setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, secretKey).compact();

	}

	public boolean validateToken(String token) {
		try {
			logger.info("validateToken: start, token:" + token);

			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

			logger.info("validateToken: Successful");
			return true;
		} catch (Exception e) {
			logger.info("validateToken: Fail");
			return false; // Token is invalid
		}
	}

	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
}
