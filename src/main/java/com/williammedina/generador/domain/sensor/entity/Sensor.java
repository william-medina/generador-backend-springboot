package com.williammedina.generador.domain.sensor.entity;

import com.williammedina.generador.domain.sensor.dto.SensorInputDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "sensors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(length = 50)
    private String event;

    @Column(name = "network_voltage")
    private Double networkVoltage;

    @Column(name = "generator_voltage")
    private Double generatorVoltage;

    @Column(name = "contactor_1", length = 15)
    private String contactor1;

    @Column(name = "contactor_2", length = 15)
    private String contactor2;

    @Column(name = "battery_voltage")
    private Double batteryVoltage;

    @Column(name = "fuel_level", length = 15)
    private String fuelLevel;

    @Column(name = "date")
    private LocalDateTime date;

    public Sensor(
            String event,
            Double networkVoltage,
            Double generatorVoltage,
            String contactor1,
            String contactor2,
            Double batteryVoltage,
            String fuelLevel,
            LocalDateTime date
    ) {
        this.event = event;
        this.networkVoltage = networkVoltage;
        this.generatorVoltage = generatorVoltage;
        this.contactor1 = contactor1;
        this.contactor2 = contactor2;
        this.batteryVoltage = batteryVoltage;
        this.fuelLevel = fuelLevel;
        this.date = date;
    }

    public String getDate() {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static Sensor fromInputDTO(SensorInputDTO data) {
        return new Sensor(
                data.event(),
                data.network_voltage(),
                data.generator_voltage(),
                data.contactor_1(),
                data.contactor_2(),
                data.battery_voltage(),
                data.fuel_level(),
                data.date()
        );
    }

}
