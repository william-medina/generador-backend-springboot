package com.williammedina.generador.domain.realTimeData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RealTimeData {

    @JsonProperty("event")
    private String event;

    @JsonProperty("network_voltage")
    private Double networkVoltage;

    @JsonProperty("generator_voltage")
    private Double generatorVoltage;

    @JsonProperty("contactor_1")
    private String contactor1;

    @JsonProperty("contactor_2")
    private String contactor2;

    @JsonProperty("battery_voltage")
    private Double batteryVoltage;

    @JsonProperty("fuel_level")
    private String fuelLevel;

    @JsonProperty("date")
    private String date;
}
