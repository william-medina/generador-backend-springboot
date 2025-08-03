package com.williammedina.generador.domain.apikey.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO used to update the activation status of an API key")
public record ApiKeyStatusDTO(

        @Schema(description = "Whether the API key should be active", example = "false")
        Boolean is_active
) {
}
