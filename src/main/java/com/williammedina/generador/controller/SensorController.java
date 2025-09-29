package com.williammedina.generador.controller;

import com.williammedina.generador.domain.sensor.service.SensorService;
import com.williammedina.generador.domain.sensor.dto.SensorDTO;
import com.williammedina.generador.domain.sensor.dto.SensorInputDTO;
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

import java.util.List;

@RestController
@RequestMapping(value = "/sensors", produces = "application/json")
@Tag(name = "Sensors", description = "Endpoints for managing sensors data.")
@AllArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @Operation(
            summary = "Save sensors",
            description = "Stores sensors data associated with a given API key.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Sensor data saved successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Invalid or inactive API key", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @PostMapping("/{api_key}")
    public ResponseEntity<SensorDTO> saveSensor(@RequestBody @Valid SensorInputDTO data, @PathVariable String api_key) {
        SensorDTO sensor = sensorService.saveSensor(data, api_key);
        return ResponseEntity.status(HttpStatus.CREATED).body(sensor);
    }

    @Operation(
            summary = "Get all sensors",
            description = "Retrieves all sensors' data stored in the system.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sensors retrieved successfully"),
            }
    )
    @GetMapping
    public ResponseEntity<List<SensorDTO>> getAllSensors() {
        List<SensorDTO> sensors = sensorService.getAllSensors();
        return ResponseEntity.ok(sensors);
    }
}
