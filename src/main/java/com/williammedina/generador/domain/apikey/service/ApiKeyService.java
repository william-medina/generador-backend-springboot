package com.williammedina.generador.domain.apikey.service;

import com.williammedina.generador.domain.apikey.dto.ApiKeyDTO;
import com.williammedina.generador.domain.apikey.dto.ApiKeyInputDTO;
import com.williammedina.generador.domain.apikey.dto.ApiKeyStatusDTO;

import java.util.List;

public interface ApiKeyService {

    ApiKeyDTO createApiKey(ApiKeyInputDTO data);
    List<ApiKeyDTO> getAllApiKeys();
    void deleteApiKey(Long id);
    ApiKeyStatusDTO toggleApiKey(Long id);

}
