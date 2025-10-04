package com.project.back_end.repo;

import com.project.back_end.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    List<Token> findByUserId(Long userId);
    List<Token> findByUserIdAndIsActive(Long userId, Boolean isActive);
    List<Token> findByExpiryBefore(LocalDateTime expiry);
    List<Token> findByUserType(String userType);
}

