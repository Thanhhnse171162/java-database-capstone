package com.project.back_end.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(nullable = false, unique = true)
    private String token;
    
    @Column(nullable = false)
    private LocalDateTime expiry;
    
    @Column
    private String userType; // DOCTOR, PATIENT, ADMIN
    
    @Column
    private Boolean isActive = true;
    
    // Constructors
    public Token() {}
    
    public Token(Long userId, String token, LocalDateTime expiry, String userType) {
        this.userId = userId;
        this.token = token;
        this.expiry = expiry;
        this.userType = userType;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public LocalDateTime getExpiry() {
        return expiry;
    }
    
    public void setExpiry(LocalDateTime expiry) {
        this.expiry = expiry;
    }
    
    public String getUserType() {
        return userType;
    }
    
    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiry);
    }
    
    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", userId=" + userId +
                ", token='" + token + '\'' +
                ", expiry=" + expiry +
                ", userType='" + userType + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}

