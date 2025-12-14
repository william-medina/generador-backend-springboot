package com.williammedina.generador.domain.apikey.service.finder;

import com.williammedina.generador.domain.apikey.entity.ApiKeyEntity;

public interface ApiKeyFinder {

    ApiKeyEntity findApiKeyById(Long id);

}
