package com.williammedina.generador.domain.realTimeData.dto;

import com.williammedina.generador.domain.realTimeData.RealTimeData;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for retrieving real-time data from the generator")
public record RealTimeDataDTO(

        @Schema(description = "Last event", example = "Restablecimiento de energia")
        String event,

        @Schema(description = "Live network voltage", example = "121.8")
        Double network_voltage,

        @Schema(description = "Live generator voltage", example = "112.0")
        Double generator_voltage,

        @Schema(description = "Live Contactor 1 state", example = "Desactivado")
        String contactor_1,

        @Schema(description = "Live Contactor 2 state", example = "Activado")
        String contactor_2,

        @Schema(description = "Live Battery voltage", example = "12.7")
        Double battery_voltage,

        @Schema(description = "Live Fuel level", example = "Bajo")
        String fuel_level,

        @Schema(description = "Timestamp of the last event", example = "2025-08-02 11:20:00")
        String date
) {
        public static RealTimeDataDTO fromEntity(RealTimeData data) {
                return new RealTimeDataDTO(
                        data.getEvent(),
                        data.getNetworkVoltage(),
                        data.getGeneratorVoltage(),
                        data.getContactor1(),
                        data.getContactor2(),
                        data.getBatteryVoltage(),
                        data.getFuelLevel(),
                        data.getDate()
                );
        }

}
