package com.williammedina.generador.domain.realTimeData.dto;

public record RealTimeDataDTO(
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
