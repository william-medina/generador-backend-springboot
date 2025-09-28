package com.williammedina.generador.domain.apikey.service;

import com.williammedina.generador.domain.apikey.dto.ApiKeyDTO;
import com.williammedina.generador.domain.apikey.dto.ApiKeyInputDTO;
import com.williammedina.generador.domain.apikey.dto.ApiKeyStatusDTO;
import com.williammedina.generador.domain.apikey.entity.ApiKeyEntity;
import com.williammedina.generador.domain.apikey.repository.ApiKeyRepository;
import com.williammedina.generador.domain.user.entity.UserEntity;
import com.williammedina.generador.infrastructure.exception.AppException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ApiKeyServiceImpl implements ApiKeyService{

    private final ApiKeyRepository apiKeyRepository;

    @Override
    @Transactional
    public ApiKeyDTO createApiKey(ApiKeyInputDTO data) {
        UserEntity user = getAuthenticatedUser();
        log.info("Creating API key for user ID: {}", user.getId());
        ApiKeyEntity apiKey = new ApiKeyEntity(data.name(), user);
        apiKeyRepository.save(apiKey);

        log.info("API key created with ID: {} for user ID: {}", apiKey.getId(), user.getId());
        return ApiKeyDTO.fromCreatedEntity(apiKey);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApiKeyDTO> getAllApiKeys() {
        UserEntity user = getAuthenticatedUser();
        log.debug("Fetching all API keys for user ID: {}", user.getId());
        List<ApiKeyEntity> apiKeys = apiKeyRepository.findByUser(user);
        return apiKeys.stream().map(ApiKeyDTO::fromEntity).toList();
    }

    @Override
    @Transactional
    public void deleteApiKey(Long id) {
        UserEntity user = getAuthenticatedUser();
        log.info("Attempting to delete API key with ID: {} by user ID: {}", id, user.getId());
        ApiKeyEntity apiKey = findApiKeyById(id);
        checkModificationPermission(apiKey);
        apiKeyRepository.delete(apiKey);
        log.info("API key with ID: {} deleted successfully by user ID: {}.", id, user.getId());
    }

    @Override
    @Transactional
    public ApiKeyStatusDTO toggleApiKey(Long id) {
        UserEntity user = getAuthenticatedUser();
        log.info("Toggling API key status for ID: {} by user ID: {}", id, user.getId());
        ApiKeyEntity apiKey = findApiKeyById(id);
        checkModificationPermission(apiKey);
        apiKey.setActive(!apiKey.isActive());
        apiKeyRepository.save(apiKey);
        log.info("API key status changed to: {} with ID: {} by user Id: {}", apiKey.isActive(), apiKey.getId(), user.getId());
        return new ApiKeyStatusDTO(apiKey.isActive());
    }

    public UserEntity getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserEntity) {
            return (UserEntity) authentication.getPrincipal();
        }

        log.error("Failed to retrieve a valid authenticated user");
        throw new IllegalStateException("El usuario autenticado no es válido.");
    }

    public ApiKeyEntity findApiKeyById(Long id) {
        return apiKeyRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("API key not found with ID: {}", id);
                    return new AppException("Api Key no encontrada", HttpStatus.NOT_FOUND);
                });
    }

    private void checkModificationPermission(ApiKeyEntity apiKey) {
        if (!apiKey.getUser().equals(getAuthenticatedUser())) {
            log.warn("User is not authorized to modify this API key.");
            throw new AppException("No tienes permiso para realizar esta acción", HttpStatus.FORBIDDEN);
        }
    }
}
