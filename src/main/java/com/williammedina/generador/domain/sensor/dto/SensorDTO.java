package com.williammedina.generador.domain.sensor.dto;

import com.williammedina.generador.domain.sensor.entity.SensorEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO representing sensor data stored per event")
public record SensorDTO(

        @Schema(description = "Sensor event log ID", example = "101")
        Long id,

        @Schema(description = "Event that triggered the record", example = "Perdida de energia")
        String event,

        @Schema(description = "Network voltage at the time of the event", example = "0.52")
        Double network_voltage,

        @Schema(description = "Generator voltage at the time of the event", example = "0.00")
        Double generator_voltage,

        @Schema(description = "Contactor 1 state", example = "Activado")
        String contactor_1,

        @Schema(description = "Contactor 2 state", example = "Desactivado")
        String contactor_2,

        @Schema(description = "Battery voltage", example = "12.6")
        Double battery_voltage,

        @Schema(description = "Fuel level reading", example = "Alto")
        String fuel_level,

        @Schema(description = "Date and time of the record", example = "2025-08-02 11:00:00")
        String date
) {

        public static SensorDTO fromEntity(SensorEntity sensor) {
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
