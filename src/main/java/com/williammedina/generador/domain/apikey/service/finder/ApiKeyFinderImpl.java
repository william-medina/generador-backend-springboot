package com.williammedina.generador.domain.apikey.service.finder;

import com.williammedina.generador.domain.apikey.entity.ApiKeyEntity;
import com.williammedina.generador.domain.apikey.repository.ApiKeyRepository;
import com.williammedina.generador.infrastructure.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiKeyFinderImpl implements ApiKeyFinder {

    private final ApiKeyRepository apiKeyRepository;

    @Override
    public ApiKeyEntity findApiKeyById(Long id) {
        return apiKeyRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("API key not found with ID: {}", id);
                    return new AppException("Api Key no encontrada", HttpStatus.NOT_FOUND);
                });
    }
}
