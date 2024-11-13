package com.hope.apiapp.dto;

import java.time.LocalDateTime;

public interface PharmacistProjection {

	Long getPharmacistId();

	String getFirstName();

	String getLastName();

	String getAddress1();

	String getAddress2();

	String getMobile();

	String getPostalCode();

	String getStatus();

	LocalDateTime getUpdatedAt();
}
