package com.williammedina.generador.domain.sensor.service;

import com.williammedina.generador.domain.sensor.dto.SensorDTO;
import com.williammedina.generador.domain.sensor.dto.SensorInputDTO;

import java.util.List;

public interface SensorService {

    List<SensorDTO> getAllSensors();
    SensorDTO saveSensor(SensorInputDTO data, String apiKey);

}
