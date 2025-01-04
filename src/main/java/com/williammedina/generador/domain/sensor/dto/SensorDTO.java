package com.williammedina.generador.domain.sensor.dto;

public record SensorDTO(
        Long id,
        String event,
        Double network_voltage,
        Double generator_voltage,
        String contactor_1,
        String contactor_2,
        Double battery_voltage,
        String fuel_level,
        String date
) {
}
