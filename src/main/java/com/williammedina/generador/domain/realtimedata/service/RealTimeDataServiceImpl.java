package com.williammedina.generador.domain.realtimedata.service;

import com.williammedina.generador.domain.apikey.service.validator.ApiKeyValidator;
import com.williammedina.generador.domain.realtimedata.model.RealTimeData;
import com.williammedina.generador.domain.realtimedata.dto.RealTimeDataDTO;
import com.williammedina.generador.domain.realtimedata.dto.RealTimeDataInputDTO;
import com.williammedina.generador.domain.realtimedata.repository.RealTimeDataRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@AllArgsConstructor
public class RealTimeDataServiceImpl implements RealTimeDataService{

    private final RealTimeDataRepository repository;
    private final ApiKeyValidator apiKeyValidator;

    @Override
    public RealTimeDataDTO saveRealTimeData(RealTimeDataInputDTO request, String apiKey) throws IOException {
        apiKeyValidator.ensureApiKeyIsActive(apiKey);

        RealTimeData existingData = repository.read();
        updateNonNullFields(existingData, request);

        repository.write(existingData);

        return RealTimeDataDTO.fromEntity(existingData);
    }

    @Override
    public RealTimeDataDTO getRealTimeData() throws IOException {
        RealTimeData data = repository.read();
        return RealTimeDataDTO.fromEntity(data);
    }

    private void updateNonNullFields(RealTimeData existing, RealTimeDataInputDTO input) {
        if (input.event() != null) existing.setEvent(input.event());
        if (input.network_voltage() != null) existing.setNetworkVoltage(input.network_voltage());
        if (input.generator_voltage() != null) existing.setGeneratorVoltage(input.generator_voltage());
        if (input.contactor_1() != null) existing.setContactor1(input.contactor_1());
        if (input.contactor_2() != null) existing.setContactor2(input.contactor_2());
        if (input.battery_voltage() != null) existing.setBatteryVoltage(input.battery_voltage());
        if (input.fuel_level() != null) existing.setFuelLevel(input.fuel_level());
        if (input.date() != null) existing.setDate(input.date());
    }

}
