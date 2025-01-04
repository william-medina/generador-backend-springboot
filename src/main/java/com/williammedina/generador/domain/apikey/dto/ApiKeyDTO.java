package com.williammedina.generador.domain.apikey.dto;

import java.time.LocalDateTime;

public record ApiKeyDTO(
        Long id,
        String name,
        String key,
        Boolean is_active,
        LocalDateTime created_at
) {
}
