package com.williammedina.generador.domain.apikey.repository;

import com.williammedina.generador.domain.apikey.entity.ApiKeyEntity;
import com.williammedina.generador.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKeyEntity, Long> {
    List<ApiKeyEntity> findByUser(UserEntity user);

    Optional<ApiKeyEntity> findByKeyAndIsActive(String key, boolean isActive);
}
