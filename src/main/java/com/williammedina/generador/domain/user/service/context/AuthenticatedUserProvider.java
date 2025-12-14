package com.williammedina.generador.domain.user.service.context;


import com.williammedina.generador.domain.user.entity.UserEntity;

public interface AuthenticatedUserProvider {

    UserEntity getAuthenticatedUser();

}
