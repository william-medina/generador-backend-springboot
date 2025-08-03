package com.williammedina.generador.domain.sensor;

import com.williammedina.generador.domain.apikey.ApiKeyRepository;
import com.williammedina.generador.domain.sensor.dto.SensorDTO;
import com.williammedina.generador.domain.sensor.dto.SensorInputDTO;
import com.williammedina.generador.infrastructure.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class SensorService {

    private final SensorRepository sensorRepository;
    private final ApiKeyRepository apiKeyRepository;

    public SensorService(SensorRepository sensorRepository, ApiKeyRepository apiKeyRepository) {
        this.sensorRepository = sensorRepository;
        this.apiKeyRepository = apiKeyRepository;
    }

    @Transactional(readOnly = true)
    public List<SensorDTO> getAllSensors() {
        log.debug("Retrieving all sensor records ordered by date");
        List<Sensor> sensors = sensorRepository.findAllByOrderByDateDesc();
        return sensors.stream().map(SensorDTO::fromEntity).toList();
    }

    @Transactional
    public SensorDTO saveSensor(SensorInputDTO data, String apiKey) {
        log.info("Saving new sensors data by event with provided API key");

        validateApiKey(apiKey);

        Sensor sensor = Sensor.fromInputDTO(data);
        Sensor savedSensor = sensorRepository.save(sensor);
        log.info("Sensors data saved by event with ID: {}", savedSensor.getId());

        return SensorDTO.fromEntity(savedSensor);
    }

    private void validateApiKey(String apiKey) {
        log.debug("Validating API key");
        apiKeyRepository.findByKeyAndIsActive(apiKey, true)
                .orElseThrow(() -> {
                    log.warn("API key validation failed: ending with {}", apiKey.substring(Math.max(apiKey.length() - 4, 0)));
                    return new AppException("Invalid or inactive API Key", HttpStatus.FORBIDDEN);
                });
    }

}
