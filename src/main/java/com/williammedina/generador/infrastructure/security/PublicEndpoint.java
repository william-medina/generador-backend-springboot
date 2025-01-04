package com.williammedina.generador.infrastructure.security;

import org.springframework.http.HttpMethod;

public record PublicEndpoint(
        String url,
        HttpMethod method
) {
}
