package com.williammedina.generador.infrastructure.security;

import com.williammedina.generador.domain.user.User;
import com.williammedina.generador.domain.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService implements UserDetailsService {

    private final UserRepository userRepository;

    public AutenticacionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = (User) userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("El usuario con el email" + email + " no fue encontrado.");
        }
        return user;
    }
}
