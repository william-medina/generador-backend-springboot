package com.williammedina.generador.infrastructure.security;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Server response with the JWT token")
public record JwtTokenResponse(
        @Schema(description = "Generated JWT token", example = "eyJhbGciOiJIUzI1NiIsInR...")
        String token
) {
}
