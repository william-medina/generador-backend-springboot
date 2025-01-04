package com.williammedina.generador.domain.sensor;

import com.williammedina.generador.domain.apikey.ApiKeyRepository;
import com.williammedina.generador.domain.sensor.dto.SensorDTO;
import com.williammedina.generador.domain.sensor.dto.SensorInputDTO;
import com.williammedina.generador.infrastructure.errors.AppException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        List<Sensor> sensors = sensorRepository.findAllByOrderByDateDesc();
        return sensors.stream().map(this::toSensorDTO).toList();
    }

    @Transactional
    public SensorDTO saveSensor(SensorInputDTO data, String apiKey) {

        validateApiKey(apiKey);

        Sensor sensor = Sensor.fromInputDTO(data);
        Sensor savedSensor = sensorRepository.save(sensor);

        return toSensorDTO(savedSensor);
    }

    private void validateApiKey(String apiKey) {
        apiKeyRepository.findByKeyAndIsActive(apiKey, true)
                .orElseThrow(() -> new AppException("Invalid or inactive API Key", "FORBIDDEN"));
    }

    public SensorDTO toSensorDTO(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.getEvent(),
                sensor.getNetworkVoltage(),
                sensor.getGeneratorVoltage(),
                sensor.getContactor1(),
                sensor.getContactor2(),
                sensor.getBatteryVoltage(),
                sensor.getFuelLevel(),
                sensor.getDate()
        );
    }
}
