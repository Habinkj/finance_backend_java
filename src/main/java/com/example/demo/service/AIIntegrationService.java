package com.example.demo.service;

import com.example.demo.dto.AIAnalysisDTO;
import com.example.demo.dto.CategorySpendDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

@Service
public class AIIntegrationService {

    // You will need to define this Bean in a config class
    @Autowired
    private RestTemplate restTemplate;

    // This will point to your future Python server (e.g., http://localhost:5000/analyze)
    @Value("${PYTHON_AI_URL:http://localhost:5000/analyze}")
    private String aiServiceUrl;

    public AIAnalysisDTO requestBehaviorAnalysis(List<CategorySpendDTO> aggregatedData) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // We package the aggregated data we built yesterday and send it to Python
        HttpEntity<List<CategorySpendDTO>> requestEntity = new HttpEntity<>(aggregatedData, headers);

        try {
            // Make the POST request to the Python service
            ResponseEntity<AIAnalysisDTO> response = restTemplate.postForEntity(
                    aiServiceUrl,
                    requestEntity,
                    AIAnalysisDTO.class
            );
            return response.getBody();
        } catch (Exception e) {
            // If Python is down, we handle it gracefully, not with a crash.
            throw new RuntimeException("AI Service is currently unavailable. Please try again later.");
        }
    }
}