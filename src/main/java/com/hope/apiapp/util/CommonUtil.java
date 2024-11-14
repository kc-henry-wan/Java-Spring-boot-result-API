package com.hope.apiapp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hope.apiapp.security.CustomUserDetails;

public class CommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	public static Long getCurrentUserId() {
		try {
			logger.info("getCurrentUserId Start");
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
			logger.info("getCurrentUserId RuntimeException");
			return null;
		}
	}

	// Method to get coordinates from an external API
	public static double[] getCoordinatesFromAddress(String address) {
		try {
			logger.info("getCoordinatesFromAddress Start");
//			// URL encode the address
//			String encodedAddress = java.net.URLEncoder.encode(address, "UTF-8");
//
//			// Use Nominatim API (or any other suitable API)
//			String url = "https://nominatim.openstreetmap.org/search?q=" + encodedAddress + "&format=json&limit=1";
//
//			RestTemplate restTemplate = new RestTemplate();
//			String response = restTemplate.getForObject(url, String.class);
//
//			// Parse the response
//			if (response != null && !response.isEmpty()) {
//				JSONArray jsonArray = new JSONArray(response);
//				if (jsonArray.length() > 0) {
//					JSONObject jsonObject = jsonArray.getJSONObject(0);
//					double latitude = jsonObject.getDouble("lat");
//					double longitude = jsonObject.getDouble("lon");
//					return new double[] { longitude, latitude };
//				}
//			}
			return new double[] { -1.49957404, 52.4188218 };

		} catch (Exception e) {
			logger.error("Failed to get coordinates for address: " + address, e);
		}
		return new double[] { 0.0, 0.0 }; // Return default coordinates if API call fails
	}

	// Haversine formula for distance calculation
	public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		double earthRadius = 3959; // miles
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lon2 - lon1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return earthRadius * c;
	}
}
