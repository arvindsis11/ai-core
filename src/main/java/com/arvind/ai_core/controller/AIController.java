package com.arvind.ai_core.controller;

import com.arvind.ai_core.api.v1.dto.ApiResponse;
import com.arvind.ai_core.api.v1.controller.ChatRequestDto;
import com.arvind.ai_core.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * API Controller for AI operations.
 * Handles all AI-related REST endpoints.
 */
@Slf4j
@RestController
@RequestMapping("/v1/ai")
@RequiredArgsConstructor
public class AIController {

    private final OpenAIService openAIService;

    @GetMapping("/ask")
    public ResponseEntity<ApiResponse<String>> askAI(@RequestParam String question) {
        log.info("Received AI question: {}", question);
        try {
            String response = openAIService.askAI(question);
            return ResponseEntity.ok(ApiResponse.success(response, "AI response generated successfully"));
        } catch (Exception e) {
            log.error("Error processing AI question", e);
            throw e;
        }
    }

    @PostMapping("/chat")
    public ResponseEntity<ApiResponse<String>> chat(@RequestBody ChatRequestDto request) {
        log.info("Received chat request: {}", request.getMessage());
        try {
            String response = openAIService.askAI(request.getMessage());
            return ResponseEntity.ok(ApiResponse.success(response, "Chat response generated successfully"));
        } catch (Exception e) {
            log.error("Error processing chat request", e);
            throw e;
        }
    }
}