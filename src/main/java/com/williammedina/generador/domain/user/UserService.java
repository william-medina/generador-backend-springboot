package com.williammedina.generador.domain.user;

import com.williammedina.generador.domain.user.dto.LoginDTO;
import com.williammedina.generador.domain.user.dto.UserDTO;
import com.williammedina.generador.infrastructure.security.JwtTokenResponse;
import com.williammedina.generador.infrastructure.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            TokenService tokenService,
            AuthenticationManager authenticationManager,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public JwtTokenResponse login(LoginDTO data) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        Authentication authenticatedUser = authenticationManager.authenticate(authenticationToken);
        String jwtToken = tokenService.generateToken((User) authenticatedUser.getPrincipal());
        return new JwtTokenResponse(jwtToken);
    }

    @Transactional(readOnly = true)
    public UserDTO getCurrentUser() {
        User user = getAuthenticatedUser();
        return toUserDTO(user);
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }

        throw new IllegalStateException("El usuario autenticado no es válido.");
    }

    public UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail()
        );
    }
}
