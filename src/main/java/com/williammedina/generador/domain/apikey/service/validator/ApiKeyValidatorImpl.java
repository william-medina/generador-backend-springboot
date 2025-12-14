package com.williammedina.generador.domain.apikey.service.validator;

import com.williammedina.generador.domain.apikey.repository.ApiKeyRepository;
import com.williammedina.generador.infrastructure.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiKeyValidatorImpl implements ApiKeyValidator {

    private final ApiKeyRepository apiKeyRepository;

    @Override
    public void ensureApiKeyIsActive(String apiKey) {
        apiKeyRepository.findByKeyAndIsActive(apiKey, true)
                .orElseThrow(() -> {
                    log.warn("API key validation failed: ending with {}", apiKey.substring(Math.max(apiKey.length() - 4, 0)));
                    return new AppException("Invalid or inactive API Key", HttpStatus.FORBIDDEN);
                });
    }
}
