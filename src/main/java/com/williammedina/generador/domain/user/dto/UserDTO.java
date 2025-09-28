package com.williammedina.generador.domain.user.dto;

import com.williammedina.generador.domain.user.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Public user information")
public record UserDTO(

        @Schema(description = "User ID", example = "1")
        Long id,

        @Schema(description = "User's email", example = "user@email.com")
        String email
) {
        public static UserDTO fromEntity(UserEntity user) {
                return new UserDTO(
                        user.getId(),
                        user.getEmail()
                );
        }
}
