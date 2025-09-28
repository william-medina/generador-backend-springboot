package com.williammedina.generador.domain.sensor.repository;

import com.williammedina.generador.domain.sensor.entity.SensorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorRepository extends JpaRepository<SensorEntity, Long> {
    List<SensorEntity> findAllByOrderByDateDesc();
}
