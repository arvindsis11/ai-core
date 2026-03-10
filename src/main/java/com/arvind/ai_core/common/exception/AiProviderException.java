package com.arvind.ai_core.common.exception;

/**
 * Exception thrown when an AI provider operation fails.
 */
public class AiProviderException extends BusinessException {
    private final String provider;

    public AiProviderException(String message, String provider) {
        super(message, "AI_PROVIDER_ERROR");
        this.provider = provider;
    }

    public AiProviderException(String message, String provider, Throwable cause) {
        super(message, cause, "AI_PROVIDER_ERROR");
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }
}
