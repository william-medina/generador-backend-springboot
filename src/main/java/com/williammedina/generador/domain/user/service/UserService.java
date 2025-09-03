package com.williammedina.generador.domain.user.service;

import com.williammedina.generador.domain.user.dto.LoginDTO;
import com.williammedina.generador.domain.user.dto.UserDTO;
import com.williammedina.generador.infrastructure.security.JwtTokenResponse;

public interface UserService {

    JwtTokenResponse login(LoginDTO data);
    UserDTO getCurrentUser();

}
