package com.hope.apiapp.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import com.hope.apiapp.security.CustomUserDetails;

public class CommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	public static Long getCurrentUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()
				&& !(authentication.getPrincipal() instanceof String)) {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
			// Assuming your UserDetails implementation has a getId() method
			return userDetails.getUserId();
		}
		return null; // Or handle as appropriate
	}

	// Method to get coordinates from an external API
	public static double[] getCoordinatesFromAddress(String address) {
		try {
			// URL encode the address
			String encodedAddress = java.net.URLEncoder.encode(address, "UTF-8");

			// Use Nominatim API (or any other suitable API)
			String url = "https://nominatim.openstreetmap.org/search?q=" + encodedAddress + "&format=json&limit=1";

			RestTemplate restTemplate = new RestTemplate();
			String response = restTemplate.getForObject(url, String.class);

			// Parse the response
			if (response != null && !response.isEmpty()) {
				JSONArray jsonArray = new JSONArray(response);
				if (jsonArray.length() > 0) {
					JSONObject jsonObject = jsonArray.getJSONObject(0);
					double latitude = jsonObject.getDouble("lat");
					double longitude = jsonObject.getDouble("lon");
					return new double[] { longitude, latitude };
				}
			}
		} catch (Exception e) {
			logger.error("Failed to get coordinates for address: " + address, e);
		}
		return new double[] { 0.0, 0.0 }; // Return default coordinates if API call fails
	}
}
