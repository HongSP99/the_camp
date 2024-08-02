package io.camp.user.repository;

import io.camp.user.model.email.AuthCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AuthCodeRepository extends JpaRepository<AuthCode, Long> {
    Optional<AuthCode> findByEmail(String email);
    void deleteByEmail(String email);
    void deleteByExpiresAtBefore(LocalDateTime expiresAt);

}