package com.williammedina.generador.domain.apikey.service.permission;

import com.williammedina.generador.domain.apikey.entity.ApiKeyEntity;
import com.williammedina.generador.domain.user.entity.UserEntity;
import com.williammedina.generador.domain.user.service.context.AuthenticatedUserProvider;
import com.williammedina.generador.infrastructure.exception.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiKeyPermissionServiceImpl implements ApiKeyPermissionService {

    private final AuthenticatedUserProvider authenticatedUserProvider;

    @Override
    public void checkCanModify(ApiKeyEntity apiKey) {
        UserEntity currentUser = authenticatedUserProvider.getAuthenticatedUser();
        if (!apiKey.getUser().equals(currentUser)) {
            log.warn("User is not authorized to modify this API key.");
            throw new AppException("No tienes permiso para realizar esta acción", HttpStatus.FORBIDDEN);
        }
    }

}
