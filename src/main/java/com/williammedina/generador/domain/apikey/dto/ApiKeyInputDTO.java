package com.williammedina.generador.domain.apikey.dto;

import jakarta.validation.constraints.NotBlank;

public record ApiKeyInputDTO(
        @NotBlank(message = "El nombre es requerido.")
        String name
) {
}
