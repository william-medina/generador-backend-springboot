package com.williammedina.generador.controller;

import com.williammedina.generador.domain.realtimedata.service.RealTimeDataService;
import com.williammedina.generador.domain.realtimedata.dto.RealTimeDataDTO;
import com.williammedina.generador.domain.realtimedata.dto.RealTimeDataInputDTO;
import com.williammedina.generador.infrastructure.exception.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/data", produces = "application/json")
@Tag(name = "Real Time Data", description = "Endpoints for handling real-time data.")
@AllArgsConstructor
public class RealTimeDataController {

    private final RealTimeDataService realTimeDataService;

    @Operation(
            summary = "Save real-time data",
            description = "Saves the provided real-time data for monitoring purposes. Requires an active API key.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Real-time data saved successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Invalid or inactive API key", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @PostMapping("/{api_key}")
    public ResponseEntity<RealTimeDataDTO> saveRealTimeData(@RequestBody @Valid RealTimeDataInputDTO data, @PathVariable String api_key) throws IOException {
        RealTimeDataDTO realTimeData = realTimeDataService.saveRealTimeData(data, api_key);
        return ResponseEntity.status(HttpStatus.CREATED).body(realTimeData);
    }

    @Operation(
            summary = "Get real-time data",
            description = "Retrieves the latest saved real-time data.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Real-time data retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Real-time data not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @GetMapping
    public ResponseEntity<RealTimeDataDTO> getRealTimeData() throws IOException {
        RealTimeDataDTO realTimeData = realTimeDataService.getRealTimeData();
        return ResponseEntity.ok(realTimeData);
    }

}
