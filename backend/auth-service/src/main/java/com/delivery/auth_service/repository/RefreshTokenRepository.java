package com.delivery.auth_service.repository;

import com.delivery.auth_service.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);

    boolean existsByToken(String token); // lỗi trước do thiếu dòng này
}
