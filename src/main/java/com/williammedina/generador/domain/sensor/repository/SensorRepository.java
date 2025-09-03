package com.williammedina.generador.domain.sensor.repository;

import com.williammedina.generador.domain.sensor.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    List<Sensor> findAllByOrderByDateDesc();
}
