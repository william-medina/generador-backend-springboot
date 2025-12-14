package com.williammedina.generador.domain.apikey.service.permission;

import com.williammedina.generador.domain.apikey.entity.ApiKeyEntity;

public interface ApiKeyPermissionService {

    void checkCanModify(ApiKeyEntity apiKey);

}
