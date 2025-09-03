package com.williammedina.generador.domain.realtimedata.service;

import com.williammedina.generador.domain.realtimedata.dto.RealTimeDataDTO;
import com.williammedina.generador.domain.realtimedata.dto.RealTimeDataInputDTO;

import java.io.IOException;

public interface RealTimeDataService {

    RealTimeDataDTO saveRealTimeData(RealTimeDataInputDTO data, String apiKey) throws IOException;
    RealTimeDataDTO getRealTimeData() throws IOException;

}
