package com.williammedina.generador.domain.realtimedata.repository;

import com.williammedina.generador.domain.realtimedata.model.RealTimeData;

import java.io.IOException;

public interface RealTimeDataRepository {

    RealTimeData read() throws IOException;
    void write(RealTimeData data) throws IOException;

}
