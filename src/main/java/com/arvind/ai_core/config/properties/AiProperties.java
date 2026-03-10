package com.arvind.ai_core.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for AI providers.
 * Externalizes AI-related configuration from application.yaml.
 */
@Data
@Component
@ConfigurationProperties(prefix = "ai")
public class AiProperties {

    private OpenAi openAi;
    private RateLimit rateLimit;
    private Cache cache;

    @Data
    public static class OpenAi {
        private String apiKey;
        private String url;
        private String model;
        private Integer timeoutSeconds;
        private Integer maxTokens;
        private Double temperature;
    }

    @Data
    public static class RateLimit {
        private Integer requestsPerMinute;
        private Integer requestsPerHour;
        private Boolean enabled;
    }

    @Data
    public static class Cache {
        private Boolean enabled;
        private Integer ttlMinutes;
        private Integer maxSize;
    }
}
