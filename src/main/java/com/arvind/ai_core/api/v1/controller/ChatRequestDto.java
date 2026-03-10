package com.arvind.ai_core.api.v1.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for chat request payload.
 *
 * NOTE: If you see compilation errors about missing jakarta.validation,
 * please run: mvn clean install
 * Or in IDE: Maven > Reload Projects
 * This will download all dependencies defined in pom.xml
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequestDto {
    @NotBlank(message = "Message cannot be blank")
    private String message;

    private String conversationId;
    private String userId;
}
