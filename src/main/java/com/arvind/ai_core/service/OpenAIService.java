package com.arvind.ai_core.service;

import com.arvind.ai_core.common.exception.AiProviderException;
import com.arvind.ai_core.service.ai.openai.OpenAIProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service for interacting with OpenAI API.
 * Handles all OpenAI-related operations and error handling.
 *
 * This service acts as a facade to the OpenAIProvider implementation,
 * maintaining backward compatibility while leveraging the new provider architecture.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAIService {

    private final OpenAIProvider openAIProvider;

    /**
     * Ask a question to the OpenAI API.
     *
     * @param question The question to ask
     * @return The AI response
     * @throws AiProviderException if the API call fails
     */
    public String askAI(String question) {
        log.info("Processing AI question via OpenAIService");
        return openAIProvider.chat(question);
    }
}