package com.shifterwebapp.shifter.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shifterwebapp.shifter.exception.ZoomMeetingException;
import com.shifterwebapp.shifter.meeting.ZoomMeetingRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class ZoomService {

    public JsonNode createMeeting(ZoomMeetingRequest meetingRequest) {
        String accessToken = getZoomAccessToken();

        String url = "https://api.zoom.us/v2/users/me/meetings";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ZoomMeetingRequest> entity = new HttpEntity<>(meetingRequest, headers);

        ResponseEntity<String> response;
        try {
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        } catch (HttpClientErrorException e) {
            throw new ZoomMeetingException("Zoom API returned an error: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.getBody());
            return jsonNode;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Zoom meeting response", e);
        }
    }

    public void deleteMeeting(String meetingId) {
        String accessToken = getZoomAccessToken();

        String url = "https://api.zoom.us/v2/meetings/" + meetingId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        } catch (HttpClientErrorException e) {
            throw new ZoomMeetingException("Failed to delete Zoom meeting: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
        }
    }


    private String getZoomAccessToken() {
        String accountId = System.getProperty("ZOOM_ACCOUNT_ID");
        String clientId = System.getProperty("ZOOM_CLIENT_ID");
        String clientSecret = System.getProperty("ZOOM_CLIENT_SECRET");

        String auth = clientId + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedAuth);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = "https://zoom.us/oauth/token?grant_type=account_credentials&account_id=" + accountId;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Parse JSON to extract only access_token
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(response.getBody());
            return jsonNode.get("access_token").asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Zoom access token", e);
        }
    }
}
