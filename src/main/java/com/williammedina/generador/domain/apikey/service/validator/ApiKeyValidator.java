package com.williammedina.generador.domain.apikey.service.validator;

public interface ApiKeyValidator {

    void ensureApiKeyIsActive(String apiKey);

}
