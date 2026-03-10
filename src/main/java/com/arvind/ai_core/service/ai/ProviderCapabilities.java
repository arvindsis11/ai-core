package com.arvind.ai_core.service.ai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the capabilities and features of an AI provider.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProviderCapabilities {
    private String provider;
    private boolean supportsStreaming;
    private boolean supportsImages;
    private boolean supportsFilesAPI;
    private boolean supportsVision;
    private boolean supportsFunctionCalling;
    private Integer maxContextTokens;
    private Integer maxOutputTokens;
    private Double costPerInputToken;
    private Double costPerOutputToken;
}
