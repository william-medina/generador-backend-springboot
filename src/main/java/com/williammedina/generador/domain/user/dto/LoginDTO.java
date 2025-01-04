package com.williammedina.generador.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank(message = "El email es requerido.")
        String email,

        @NotBlank(message = "El password es requerido.")
        String password
) {
}
