package com.backend.repository;

import com.backend.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByEmail(String email);
    Optional<RefreshToken> findByToken(String token);
}
