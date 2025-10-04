package com.project.back_end.services;

import com.project.back_end.models.Token;
import com.project.back_end.repo.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {
    
    @Autowired
    private TokenRepository tokenRepository;
    
    public Token generateToken(Long userId, String userType) {
        // Invalidate existing tokens for this user
        invalidateUserTokens(userId);
        
        String tokenValue = UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusHours(24); // Token expires in 24 hours
        
        Token token = new Token(userId, tokenValue, expiry, userType);
        return tokenRepository.save(token);
    }
    
    public Optional<Token> validateToken(String tokenValue) {
        Optional<Token> token = tokenRepository.findByToken(tokenValue);
        if (token.isPresent()) {
            Token t = token.get();
            if (t.getIsActive() && !t.isExpired()) {
                return token;
            } else {
                // Mark token as inactive if expired
                t.setIsActive(false);
                tokenRepository.save(t);
            }
        }
        return Optional.empty();
    }
    
    public void invalidateToken(String tokenValue) {
        Optional<Token> token = tokenRepository.findByToken(tokenValue);
        if (token.isPresent()) {
            Token t = token.get();
            t.setIsActive(false);
            tokenRepository.save(t);
        }
    }
    
    public void invalidateUserTokens(Long userId) {
        List<Token> userTokens = tokenRepository.findByUserIdAndIsActive(userId, true);
        for (Token token : userTokens) {
            token.setIsActive(false);
            tokenRepository.save(token);
        }
    }
    
    public List<Token> getUserTokens(Long userId) {
        return tokenRepository.findByUserId(userId);
    }
    
    public void deleteExpiredTokens() {
        List<Token> expiredTokens = tokenRepository.findByExpiryBefore(LocalDateTime.now());
        for (Token token : expiredTokens) {
            token.setIsActive(false);
            tokenRepository.save(token);
        }
    }
    
    public boolean isTokenValid(String tokenValue) {
        return validateToken(tokenValue).isPresent();
    }
    
    public Optional<Long> getUserIdFromToken(String tokenValue) {
        Optional<Token> token = validateToken(tokenValue);
        return token.map(Token::getUserId);
    }
    
    public Optional<String> getUserTypeFromToken(String tokenValue) {
        Optional<Token> token = validateToken(tokenValue);
        return token.map(Token::getUserType);
    }
}

