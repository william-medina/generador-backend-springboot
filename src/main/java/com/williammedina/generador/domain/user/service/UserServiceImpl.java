package com.williammedina.generador.domain.user.service;

import com.williammedina.generador.domain.user.dto.LoginDTO;
import com.williammedina.generador.domain.user.dto.UserDTO;
import com.williammedina.generador.domain.user.entity.User;
import com.williammedina.generador.infrastructure.security.JwtTokenResponse;
import com.williammedina.generador.infrastructure.security.TokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;


    @Override
    @Transactional
    public JwtTokenResponse login(LoginDTO data) {
        log.info("Attempting to authenticate user: {}", data.email());

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        Authentication authenticatedUser = authenticationManager.authenticate(authenticationToken);
        String jwtToken = tokenService.generateToken((User) authenticatedUser.getPrincipal());
        User user = (User) authenticatedUser.getPrincipal();
        log.info("User authenticated successfully. ID: {}", user.getId());

        return new JwtTokenResponse(jwtToken);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getCurrentUser() {
        User user = getAuthenticatedUser();
        log.debug("Retrieving user data. ID: {}", user.getId());
        return UserDTO.fromEntity(user);
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }

        log.error("Failed to retrieve a valid authenticated user");
        throw new IllegalStateException("El usuario autenticado no es válido.");
    }

}
