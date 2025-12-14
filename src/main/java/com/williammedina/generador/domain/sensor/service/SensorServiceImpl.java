package com.williammedina.generador.domain.sensor.service;

import com.williammedina.generador.domain.apikey.service.validator.ApiKeyValidator;
import com.williammedina.generador.domain.sensor.dto.SensorDTO;
import com.williammedina.generador.domain.sensor.dto.SensorInputDTO;
import com.williammedina.generador.domain.sensor.entity.SensorEntity;
import com.williammedina.generador.domain.sensor.repository.SensorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;
    private final ApiKeyValidator apiKeyValidator;

    @Override
    @Transactional(readOnly = true)
    public List<SensorDTO> getAllSensors() {
        log.debug("Retrieving all sensor records ordered by date");
        List<SensorEntity> sensors = sensorRepository.findAllByOrderByDateDesc();
        return sensors.stream().map(SensorDTO::fromEntity).toList();
    }

    @Override
    @Transactional
    public SensorDTO saveSensor(SensorInputDTO request, String apiKey) {
        log.info("Saving new sensors data by event with provided API key");

        apiKeyValidator.validateApiKey(apiKey);

        SensorEntity sensor = SensorEntity.fromInputDTO(request);
        SensorEntity savedSensor = sensorRepository.save(sensor);
        log.info("Sensors data saved by event with ID: {}", savedSensor.getId());

        return SensorDTO.fromEntity(savedSensor);
    }

}
