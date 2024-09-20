package com.isec.jbarros.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class NLPService {

    private final RestTemplate restTemplate;

    public NLPService() {
        this.restTemplate = new RestTemplate();
    }

    public Map<String, Object> processText(String articleId, String text, String modelId) {
        String url = "http://localhost:8081/process";  // Python API endpoint

        // Prepare request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Prepare request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("article_id", articleId);
        requestBody.put("text", text);
        requestBody.put("model_id", modelId);

        // Build the HTTP request
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // Send POST request to Python API
            ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                Map.class
            );

            // Handle the response
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to process text. HTTP error code: " + response.getStatusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error calling NLP API", e);
        }
    }
}
