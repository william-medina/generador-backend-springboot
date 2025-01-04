package com.williammedina.generador.domain.apikey;

import com.williammedina.generador.domain.apikey.dto.ApiKeyDTO;
import com.williammedina.generador.domain.apikey.dto.ApiKeyInputDTO;
import com.williammedina.generador.domain.apikey.dto.ApiKeyStatusDTO;
import com.williammedina.generador.domain.user.User;
import com.williammedina.generador.infrastructure.errors.AppException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Transactional
    public ApiKeyDTO createApiKey(ApiKeyInputDTO data) {
        User user = getAuthenticatedUser();
        ApiKey apiKey = new ApiKey(data.name(), user);
        apiKeyRepository.save(apiKey);
        return toApiKeyCreatedDTO(apiKey);
    }

    @Transactional(readOnly = true)
    public List<ApiKeyDTO> getAllApiKeys() {
        User user = getAuthenticatedUser();
        List<ApiKey> apiKeys = apiKeyRepository.findByUser(user);
        return apiKeys.stream().map(this::toApiKeyDTO).toList();
    }

    @Transactional
    public void deleteApiKey(Long id) {
        ApiKey apiKey = findApiKeyById(id);
        checkModificationPermission(apiKey);
        apiKeyRepository.delete(apiKey);
    }

    @Transactional
    public ApiKeyStatusDTO toggleApiKey(Long id) {
        ApiKey apiKey = findApiKeyById(id);
        checkModificationPermission(apiKey);
        apiKey.setActive(!apiKey.isActive());
        apiKeyRepository.save(apiKey);
        return new ApiKeyStatusDTO(apiKey.isActive());
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }

        throw new IllegalStateException("El usuario autenticado no es válido.");
    }

    public ApiKey findApiKeyById(Long id) {
        return apiKeyRepository.findById(id)
                .orElseThrow(() -> new AppException("Api Key no encontrada", "NOT_FOUND"));
    }

    private void checkModificationPermission(ApiKey apiKey) {
        if (!apiKey.getUser().equals(getAuthenticatedUser())) {
            throw new AppException("No tienes permiso para realizar esta acción", "FORBIDDEN");
        }
    }

    public ApiKeyDTO toApiKeyCreatedDTO(ApiKey apiKey) {
        return new ApiKeyDTO(
                apiKey.getId(),
                apiKey.getName(),
                apiKey.getKey(),
                apiKey.isActive(),
                apiKey.getCreatedAt()
        );
    }

    public ApiKeyDTO toApiKeyDTO(ApiKey apiKey) {
        String key = apiKey.getKey();

        key = key.substring(0, 4) + "..." + key.substring(key.length() - 3);

        return new ApiKeyDTO(
                apiKey.getId(),
                apiKey.getName(),
                key,
                apiKey.isActive(),
                apiKey.getCreatedAt()
        );
    }
}
