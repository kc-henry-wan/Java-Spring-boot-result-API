package com.hope.apiapp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.hope.apiapp.security.CustomUserDetails;
import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageForwardRequest;
import com.opencagedata.jopencage.model.JOpenCageLatLng;
import com.opencagedata.jopencage.model.JOpenCageResponse;

@Component
public class CommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	@Value("${ooK}")
	private String opencageApiKey;

	public Long getCurrentUserId() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authentication != null && authentication.isAuthenticated()
					&& !(authentication.getPrincipal() instanceof String)) {
				CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
				// Assuming your UserDetails implementation has a getId() method

				return userDetails.getUserId();
			}

			logger.info("getCurrentUserId Failed");
			return null; // Or handle as appropriate
		} catch (RuntimeException ex) {
			logger.info("getCurrentUserId RuntimeException" + ex.toString());
			return null;
		}
	}

	public Double[] getUserCoordinates() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authentication != null && authentication.isAuthenticated()
					&& !(authentication.getPrincipal() instanceof String)) {
				CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
				// Assuming your UserDetails implementation has a getId() method

				return new Double[] { userDetails.getLatitude(), userDetails.getLongitude() };
			}

			logger.info("getUserCoordinates Failed");
			return new Double[] { 0.0, 0.0 };
		} catch (RuntimeException ex) {
			logger.info("getUserCoordinates RuntimeException=" + ex.toString());
			return new Double[] { 0.0, 0.0 };
		}
	}

	public Double[] getCoordinatesFromAddress(String address) {

		try {
			JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder(opencageApiKey);

			JOpenCageForwardRequest request = new JOpenCageForwardRequest(address);
			request.setMinConfidence(1);
			request.setRestrictToCountryCode("gb");
			request.setNoAnnotations(false);
			request.setNoDedupe(true);
			JOpenCageResponse response = jOpenCageGeocoder.forward(request);
			if (response != null) {
				JOpenCageLatLng firstResultLatLng = response.getFirstPosition();

				return new Double[] { firstResultLatLng.getLat(), firstResultLatLng.getLng() };
			} else {
				logger.info("getCoordinatesFromAddress Failed");
				return new Double[] { 0.0, 0.0 };
			}
		} catch (RuntimeException ex) {
			logger.info("getCoordinatesFromAddress RuntimeException=" + ex.toString());
			return new Double[] { 0.0, 0.0 };
		}
	}

	// Haversine formula for distance calculation
	public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		double earthRadius = 3959; // miles
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lon2 - lon1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return earthRadius * c;
	}
}
