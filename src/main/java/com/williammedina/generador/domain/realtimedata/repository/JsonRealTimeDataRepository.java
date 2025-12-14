package com.williammedina.generador.domain.realtimedata.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.williammedina.generador.domain.realtimedata.model.RealTimeData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JsonRealTimeDataRepository implements RealTimeDataRepository {

    private final Path jsonPath = Path.of("data/real_time_data.json");
    private final ObjectMapper objectMapper;

    @Override
    public RealTimeData read() throws IOException {
        if (Files.notExists(jsonPath)) {
            log.info("JSON file not found. Creating new file at: {}", jsonPath);
            Files.createDirectories(jsonPath.getParent());
            Files.writeString(jsonPath, "{}");
            return new RealTimeData();
        }

        String jsonContent = Files.readString(jsonPath);
        if (jsonContent.isBlank()) return new RealTimeData();
        return objectMapper.readValue(jsonContent, RealTimeData.class);
    }

    @Override
    public void write(RealTimeData data) throws IOException {
        String updatedJsonString = objectMapper.writeValueAsString(data);
        Files.writeString(jsonPath, updatedJsonString);
        log.info("Real-time data updated successfully");
    }
}
