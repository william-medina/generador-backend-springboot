package com.williammedina.generador.domain.apikey.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "DTO containing API key details")
public record ApiKeyDTO(

        @Schema(description = "API key ID", example = "5")
        Long id,

        @Schema(description = "Name assigned to the API key", example = "Main System")
        String name,

        @Schema(description = "Generated API key string", example = "WcezBCYM0sdyAL6EKjIDm...")
        String key,

        @Schema(description = "Whether the API key is currently active", example = "true")
        Boolean is_active,

        @Schema(description = "Creation date of the API key", example = "2025-08-01 14:30:00")
        LocalDateTime created_at
) {
}
