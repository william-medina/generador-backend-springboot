package com.williammedina.generador.domain.realtimedata.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.williammedina.generador.domain.apikey.repository.ApiKeyRepository;
import com.williammedina.generador.domain.realtimedata.model.RealTimeData;
import com.williammedina.generador.domain.realtimedata.dto.RealTimeDataDTO;
import com.williammedina.generador.domain.realtimedata.dto.RealTimeDataInputDTO;
import com.williammedina.generador.infrastructure.exception.AppException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
@AllArgsConstructor
public class RealTimeDataServiceImpl implements RealTimeDataService{

    private final ApiKeyRepository apiKeyRepository;
    private final Path jsonPath = Path.of("data/real_time_data.json");

    @Override
    public RealTimeDataDTO saveRealTimeData(RealTimeDataInputDTO data, String apiKey) throws IOException {

        validateApiKey(apiKey);

        // Crear archivo/directorio si no existen
        Files.createDirectories(jsonPath.getParent());
        if (Files.notExists(jsonPath)) {
            log.info("JSON file not found. Creating new file at: {}", jsonPath);
            Files.createFile(jsonPath);
            Files.writeString(jsonPath, "{}");
        }

        ObjectMapper objectMapper = new ObjectMapper();

        // Leer los datos existentes del archivo JSON
        RealTimeData existingData;
        String jsonContent = Files.readString(jsonPath);
        if (jsonContent.isBlank()) {
            existingData = new RealTimeData();
        } else {
            existingData = objectMapper.readValue(jsonContent, RealTimeData.class);
        }

        // Actualizar solo los campos que no sean null
        if (data.event() != null) existingData.setEvent(data.event());
        if (data.network_voltage() != null) existingData.setNetworkVoltage(data.network_voltage());
        if (data.generator_voltage() != null) existingData.setGeneratorVoltage(data.generator_voltage());
        if (data.contactor_1() != null) existingData.setContactor1(data.contactor_1());
        if (data.contactor_2() != null) existingData.setContactor2(data.contactor_2());
        if (data.battery_voltage() != null) existingData.setBatteryVoltage(data.battery_voltage());
        if (data.fuel_level() != null) existingData.setFuelLevel(data.fuel_level());
        if (data.date() != null) existingData.setDate(data.date());

        // Guardar los datos actualizados en el archivo JSON
        String updatedJsonString = objectMapper.writeValueAsString(existingData);
        Files.writeString(jsonPath, updatedJsonString);

        return RealTimeDataDTO.fromEntity(existingData);
    }

    @Override
    public RealTimeDataDTO getRealTimeData() throws IOException {

        if (Files.notExists(jsonPath)) {
            log.error("JSON file not found at path: {}", jsonPath);
            throw new IOException("File not found: " + jsonPath.toString());
        }

        String jsonContent = Files.readString(jsonPath);

        ObjectMapper objectMapper = new ObjectMapper();

        RealTimeData data = objectMapper.readValue(jsonContent, RealTimeData.class);

        return RealTimeDataDTO.fromEntity(data);
    }

    private void validateApiKey(String apiKey) {
        apiKeyRepository.findByKeyAndIsActive(apiKey, true)
                .orElseThrow(() -> {
                    log.warn("API key validation failed: {}", apiKey);
                    return new AppException("Invalid or inactive API Key", HttpStatus.FORBIDDEN);
                });
    }
}
