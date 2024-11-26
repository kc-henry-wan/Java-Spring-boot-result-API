package com.hope.apiapp.exception;

import java.security.SignatureException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hope.apiapp.helper.ApiResponseFail;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ApiResponseFail> handleExpiredJwtException(ExpiredJwtException ex) {
		logger.error("JE003 - handleExpiredJwtException ex=" + ex.getMessage());
		return new ResponseEntity<>(new ApiResponseFail("JE003", "Token has expired"), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler({ MalformedJwtException.class, SignatureException.class, UnsupportedJwtException.class })
	public ResponseEntity<ApiResponseFail> handleInvalidJwtException(Exception ex) {
		logger.error("JE002 - handleInvalidJwtException ex=" + ex.getMessage());
		return new ResponseEntity<>(new ApiResponseFail("JE002", "Invalid token"), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(JwtException.class)
	public ResponseEntity<ApiResponseFail> handleJwtException(Exception ex) {
		logger.error("JE001 - handleJwtExceptionn ex=" + ex.getMessage());
		return new ResponseEntity<>(new ApiResponseFail("JE001", "Unauthorized request"), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponseFail> handleIllegalArgumentException(Exception ex) {
		logger.error("IA001 - handleIllegalArgumentExceptionn ex=" + ex.getMessage());
		return new ResponseEntity<>(new ApiResponseFail("IA001", ex.getMessage()), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiResponseFail> handleAccessDeniedException(AccessDeniedException ex) {
		logger.error("AD001 - handleAccessDeniedException ex=" + ex.getMessage());
		return new ResponseEntity<>(new ApiResponseFail("AD001", "Unauthorized request"), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiResponseFail> handleBadCredentialsException(BadCredentialsException ex) {
		logger.error("BC001 - handleBadCredentialsException ex=" + ex.getMessage());
		return new ResponseEntity<>(new ApiResponseFail("BC001", "Invalid username or password."),
				HttpStatus.UNAUTHORIZED);

	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseFail> handleResourceNotFoundException(ResourceNotFoundException ex) {
		logger.warn("handleResourceNotFoundException ex=" + ex.getMessage());
		return new ResponseEntity<>(new ApiResponseFail(ex.getMessage(), "Resource Not Found."), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ResourceConflictException.class)
	public ResponseEntity<ApiResponseFail> handleResourceConflictException(ResourceConflictException ex) {
		logger.warn("handleResourceConflictException ex=" + ex.getMessage());
		return new ResponseEntity<>(new ApiResponseFail(ex.getMessage(), "Record has been modified by another user."),
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponseFail> handleGeneralException(Exception ex) {
		logger.error("ERR001 - handleGeneralException ex=" + ex.getMessage());
		return new ResponseEntity<>(new ApiResponseFail("ERR001", "System error, please try again later"),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
