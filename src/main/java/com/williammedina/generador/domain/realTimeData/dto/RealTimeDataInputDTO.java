package com.williammedina.generador.domain.realTimeData.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record RealTimeDataInputDTO(
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
