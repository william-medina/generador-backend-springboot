package com.williammedina.generador.domain.sensor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    List<Sensor> findAllByOrderByDateDesc();
}
