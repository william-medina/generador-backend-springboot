package com.williammedina.generador.domain.apikey.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO used to create a new API key")
public record ApiKeyInputDTO(

        @Schema(description = "Name for identifying the API key", example = "External Device 3")
        @NotBlank(message = "El nombre es requerido.")
        String name
) {
}
