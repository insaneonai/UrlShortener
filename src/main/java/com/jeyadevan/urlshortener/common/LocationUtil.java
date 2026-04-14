package com.jeyadevan.urlshortener.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;


@Component
public class LocationUtil {

    public String getLocationFromIP(HttpServletRequest request){

        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }

        if (ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[0].trim();
        }

        if ("0:0:0:0:0:0:0:1".equals(ipAddress) || "::1".equals(ipAddress) || "127.0.0.1".equals(ipAddress)) {
            return "Localhost";
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://ip-api.com/json/" + ipAddress;
        System.out.println("Fetching location for IP: " + url);
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject jsonObject = new JSONObject(response.getBody());
                return jsonObject.optString("country", "Unknown");
            }
            return "Unknown";
        }
            catch (Exception e) {
                return "Unknown";
            }
    }
    
}
