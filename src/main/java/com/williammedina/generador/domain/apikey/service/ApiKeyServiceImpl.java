package com.williammedina.generador.domain.apikey.service;

import com.williammedina.generador.domain.apikey.dto.ApiKeyDTO;
import com.williammedina.generador.domain.apikey.dto.ApiKeyInputDTO;
import com.williammedina.generador.domain.apikey.dto.ApiKeyStatusDTO;
import com.williammedina.generador.domain.apikey.entity.ApiKeyEntity;
import com.williammedina.generador.domain.apikey.repository.ApiKeyRepository;
import com.williammedina.generador.domain.apikey.service.finder.ApiKeyFinder;
import com.williammedina.generador.domain.apikey.service.permission.ApiKeyPermissionService;
import com.williammedina.generador.domain.user.entity.UserEntity;
import com.williammedina.generador.domain.user.service.context.AuthenticatedUserProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ApiKeyServiceImpl implements ApiKeyService{

    private final ApiKeyRepository apiKeyRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final ApiKeyFinder apiKeyFinder;
    private final ApiKeyPermissionService apiKeyPermissionService;

    @Override
    @Transactional
    public ApiKeyDTO createApiKey(ApiKeyInputDTO data) {
        UserEntity currentUser = authenticatedUserProvider.getAuthenticatedUser();
        log.info("Creating API key for user ID: {}", currentUser.getId());
        ApiKeyEntity createdApiKey = apiKeyRepository.save(new ApiKeyEntity(data.name(), currentUser));

        log.info("API key created with ID: {} for user ID: {}", createdApiKey.getId(), currentUser.getId());
        return ApiKeyDTO.fromCreatedEntity(createdApiKey);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApiKeyDTO> getAllApiKeys() {
        UserEntity currentUser = authenticatedUserProvider.getAuthenticatedUser();
        log.debug("Fetching all API keys for user ID: {}", currentUser.getId());
        List<ApiKeyEntity> apiKeys = apiKeyRepository.findByUser(currentUser);
        return apiKeys.stream().map(ApiKeyDTO::fromEntity).toList();
    }

    @Override
    @Transactional
    public void deleteApiKey(Long id) {
        UserEntity currentUser = authenticatedUserProvider.getAuthenticatedUser();
        log.info("Attempting to delete API key with ID: {} by user ID: {}", id, currentUser.getId());
        ApiKeyEntity apiKeyToDelete = apiKeyFinder.findApiKeyById(id);
        apiKeyPermissionService.checkCanModify(apiKeyToDelete);
        apiKeyRepository.delete(apiKeyToDelete);
        log.info("API key with ID: {} deleted successfully by user ID: {}.", id, currentUser.getId());
    }

    @Override
    @Transactional
    public ApiKeyStatusDTO toggleApiKey(Long id) {
        UserEntity currentUser = authenticatedUserProvider.getAuthenticatedUser();
        log.info("Toggling API key status for ID: {} by user ID: {}", id, currentUser.getId());
        ApiKeyEntity apiKey = apiKeyFinder.findApiKeyById(id);
        apiKeyPermissionService.checkCanModify(apiKey);
        apiKey.setActive(!apiKey.isActive());
        apiKeyRepository.save(apiKey);
        log.info("API key status changed to: {} with ID: {} by user Id: {}", apiKey.isActive(), apiKey.getId(), currentUser.getId());
        return new ApiKeyStatusDTO(apiKey.isActive());
    }

}
