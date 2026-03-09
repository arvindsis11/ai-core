package com.arvind.ai_core.controller;

import com.arvind.ai_core.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AIController {

    private final OpenAIService openAIService;

    public AIController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @GetMapping("/ask")
    public String askAI(@RequestParam String question) {

        return openAIService.askAI(question);

    }
}