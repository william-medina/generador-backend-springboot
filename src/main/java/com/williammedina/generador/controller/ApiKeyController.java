package com.williammedina.generador.controller;


import com.williammedina.generador.domain.apikey.ApiKeyService;
import com.williammedina.generador.domain.apikey.dto.ApiKeyDTO;
import com.williammedina.generador.domain.apikey.dto.ApiKeyInputDTO;
import com.williammedina.generador.domain.apikey.dto.ApiKeyStatusDTO;
import com.williammedina.generador.infrastructure.errors.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api-keys", produces = "application/json")
@Tag(name = "Api Keys", description = "Endpoints for managing API keys.")
public class ApiKeyController {

    private final ApiKeyService apiKeyService;

    public ApiKeyController(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @Operation(
            summary = "Create a new API key",
            description = "Generates and returns a new API key for the authenticated user.",
            security = @SecurityRequirement(name = "bearer-key"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "API key created successfully", content = @Content(schema = @Schema(implementation = ApiKeyDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PostMapping
    public ResponseEntity<ApiKeyDTO> createApiKey(@RequestBody @Valid ApiKeyInputDTO data) {
        ApiKeyDTO apiKey = apiKeyService.createApiKey(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiKey);
    }

    @Operation(
            summary = "Get all API keys",
            description = "Retrieves all API keys for the authenticated user.",
            security = @SecurityRequirement(name = "bearer-key"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "API keys retrieved successfully", content = @Content(schema = @Schema(implementation = ApiKeyDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - invalid bearer token", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<ApiKeyDTO>> getAllApiKeys() {
        List<ApiKeyDTO> apiKeys = apiKeyService.getAllApiKeys();
        return ResponseEntity.ok(apiKeys);
    }


    @Operation(
            summary = "Delete an API key",
            description = "Deletes a specific API key for the authenticated user.",
            security = @SecurityRequirement(name = "bearer-key"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "API key deleted successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - invalid bearer token", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "API key not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApiKey(@PathVariable Long id) {
        apiKeyService.deleteApiKey(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Toggle the status of an API key",
            description = "Activates or deactivates an API key for the authenticated user.",
            security = @SecurityRequirement(name = "bearer-key"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "API key status updated successfully", content = @Content(schema = @Schema(implementation = ApiKeyStatusDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - invalid bearer token", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "API key not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<ApiKeyStatusDTO> toggleApiKey(@PathVariable Long id) {
        ApiKeyStatusDTO status = apiKeyService.toggleApiKey(id);
        return ResponseEntity.ok(status);
    }

}
