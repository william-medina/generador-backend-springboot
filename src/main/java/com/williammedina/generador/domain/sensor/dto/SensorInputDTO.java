package com.williammedina.generador.domain.sensor.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record SensorInputDTO(
        String event,
        Double network_voltage,
        Double generator_voltage,
        String contactor_1,
        String contactor_2,
        Double battery_voltage,
        String fuel_level,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime date
) {
}
