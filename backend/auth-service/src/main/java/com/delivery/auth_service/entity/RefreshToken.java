package com.delivery.auth_service.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private String email;

    private Instant expiryDate;

    public RefreshToken() {
    }

    public RefreshToken(String token, String email, Instant expiryDate) {
        this.token = token;
        this.email = email;
        this.expiryDate = expiryDate;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }
}
