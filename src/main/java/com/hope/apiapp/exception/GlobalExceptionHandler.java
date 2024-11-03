package com.hope.apiapp.exception;

import java.security.SignatureException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hope.apiapp.util.ApiResponseFail;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ApiResponseFail> handleExpiredJwtException(ExpiredJwtException ex) {
		return new ResponseEntity<>(new ApiResponseFail("401", "Token has expired"), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler({ MalformedJwtException.class, SignatureException.class, UnsupportedJwtException.class })
	public ResponseEntity<ApiResponseFail> handleInvalidJwtException(Exception ex) {
		return new ResponseEntity<>(new ApiResponseFail("401", "Invalid token"), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponseFail> handleIllegalArgumentException(IllegalArgumentException ex) {
		return new ResponseEntity<>(new ApiResponseFail("400", "Token is missing or invalid"), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiResponseFail> handleBadCredentialsException(BadCredentialsException ex) {
		return new ResponseEntity<>(new ApiResponseFail("401", ex.getMessage()), HttpStatus.UNAUTHORIZED);

	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseFail> handleResourceNotFoundException(ResourceNotFoundException ex) {
		return new ResponseEntity<>(new ApiResponseFail("401", ex.getMessage()), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(ResourceConflictException.class)
	public ResponseEntity<ApiResponseFail> ResourceConflictException(ResourceConflictException ex) {
		return new ResponseEntity<>(new ApiResponseFail("404", ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponseFail> handleGeneralException(Exception ex) {
		return new ResponseEntity<>(new ApiResponseFail("500", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
