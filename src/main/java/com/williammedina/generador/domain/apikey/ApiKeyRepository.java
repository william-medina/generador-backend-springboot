package com.williammedina.generador.domain.apikey;

import com.williammedina.generador.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    List<ApiKey> findByUser(User user);

    Optional<ApiKey> findByKeyAndIsActive(String key, boolean isActive);
}
