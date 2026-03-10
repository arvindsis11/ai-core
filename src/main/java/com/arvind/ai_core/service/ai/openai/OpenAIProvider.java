package com.arvind.ai_core.service.ai.openai;

import com.arvind.ai_core.config.properties.AiProperties;
import com.arvind.ai_core.common.exception.AiProviderException;
import com.arvind.ai_core.service.ai.AIProvider;
import com.arvind.ai_core.service.ai.ProviderCapabilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * OpenAI provider implementation.
 * Handles all interactions with OpenAI API (GPT-4, GPT-3.5, etc.)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OpenAIProvider implements AIProvider {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final AiProperties aiProperties;

    @Override
    public String chat(String message) {
        if (message == null || message.trim().isEmpty()) {
            throw new AiProviderException("Message cannot be empty", "OpenAI");
        }

        try {
            log.debug("Sending chat request to OpenAI for message: {}", message);

            String url = aiProperties.getOpenAi().getUrl();
            String apiKey = aiProperties.getOpenAi().getApiKey();
            String model = aiProperties.getOpenAi().getModel();

            if (apiKey == null || apiKey.isEmpty() || "sk-test-key".equals(apiKey)) {
                throw new AiProviderException("OpenAI API key not configured", "OpenAI");
            }

            Map<String, Object> body = buildRequestBody(message, model);
            HttpEntity<Map<String, Object>> request = buildHttpEntity(body, apiKey);

            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            if (response.getBody() == null) {
                throw new AiProviderException("Empty response from OpenAI", "OpenAI");
            }

            return extractResponseContent(response.getBody());

        } catch (RestClientException e) {
            log.error("REST client error while calling OpenAI", e);
            throw new AiProviderException(
                    "Failed to communicate with OpenAI: " + e.getMessage(),
                    "OpenAI",
                    e
            );
        } catch (Exception e) {
            log.error("Unexpected error while processing OpenAI response", e);
            throw new AiProviderException(
                    "Error processing OpenAI response: " + e.getMessage(),
                    "OpenAI",
                    e
            );
        }
    }

    @Override
    public String getProviderName() {
        return "OpenAI";
    }

    @Override
    public boolean isAvailable() {
        String apiKey = aiProperties.getOpenAi().getApiKey();
        return apiKey != null && !apiKey.isEmpty() && !apiKey.equals("sk-test-key");
    }

    @Override
    public ProviderCapabilities getCapabilities() {
        return ProviderCapabilities.builder()
                .provider("OpenAI")
                .supportsStreaming(true)
                .supportsImages(true)
                .supportsFilesAPI(true)
                .supportsVision(true)
                .supportsFunctionCalling(true)
                .maxContextTokens(128000)
                .maxOutputTokens(4096)
                .costPerInputToken(0.000003)
                .costPerOutputToken(0.000006)
                .build();
    }

    private Map<String, Object> buildRequestBody(String message, String model) {
        Map<String, Object> body = new HashMap<>();
        body.put("model", model);

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> msg = new HashMap<>();
        msg.put("role", "user");
        msg.put("content", message);
        messages.add(msg);

        body.put("messages", messages);

        if (aiProperties.getOpenAi().getMaxTokens() != null) {
            body.put("max_tokens", aiProperties.getOpenAi().getMaxTokens());
        }
        if (aiProperties.getOpenAi().getTemperature() != null) {
            body.put("temperature", aiProperties.getOpenAi().getTemperature());
        }

        return body;
    }

    private HttpEntity<Map<String, Object>> buildHttpEntity(
            Map<String, Object> body,
            String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        return new HttpEntity<>(body, headers);
    }

    private String extractResponseContent(Map response) {
        try {
            List choices = (List) response.get("choices");
            if (choices == null || choices.isEmpty()) {
                throw new AiProviderException("No choices in OpenAI response", "OpenAI");
            }

            Map firstChoice = (Map) choices.get(0);
            Map message = (Map) firstChoice.get("message");

            if (message == null || !message.containsKey("content")) {
                throw new AiProviderException("No content in OpenAI response message", "OpenAI");
            }

            return message.get("content").toString();
        } catch (ClassCastException e) {
            log.error("Error parsing OpenAI response structure", e);
            throw new AiProviderException(
                    "Invalid response structure from OpenAI",
                    "OpenAI",
                    e
            );
        }
    }
}
