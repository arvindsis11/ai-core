package com.arvind.ai_core.service.ai;

/**
 * Base interface for AI providers.
 * All AI provider implementations must implement this interface.
 *
 * This is the foundation of the multi-provider architecture that allows
 * pluggable support for OpenAI, Azure, Anthropic, Ollama, and other providers.
 */
public interface AIProvider {

    /**
     * Send a message to the AI provider and get a response.
     *
     * @param message The message/question to send
     * @return The AI provider's response
     * @throws com.arvind.ai_core.common.exception.AiProviderException if the operation fails
     */
    String chat(String message);

    /**
     * Get the name of this AI provider.
     *
     * @return Provider name (e.g., "OpenAI", "Azure", "Anthropic")
     */
    String getProviderName();

    /**
     * Check if this provider is available/configured.
     *
     * @return true if the provider is ready to use, false otherwise
     */
    boolean isAvailable();

    /**
     * Get supported features for this provider.
     *
     * @return Provider capabilities
     */
    ProviderCapabilities getCapabilities();
}
