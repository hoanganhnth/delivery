package com.delivery.auth_service.service;

import com.delivery.auth_service.dto.AuthResponse;
import com.delivery.auth_service.dto.LoginRequest;
import com.delivery.auth_service.dto.RefreshTokenRequest;
import com.delivery.auth_service.dto.RegisterRequest;
import com.delivery.auth_service.entity.AuthAccount;
import com.delivery.auth_service.repository.AuthAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private AuthAccountRepository authAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    // Register
    public void register(RegisterRequest request) {
        Optional<AuthAccount> existing = authAccountRepository.findByEmail(request.getEmail());
        if (existing.isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        AuthAccount account = new AuthAccount();
        account.setEmail(request.getEmail());
        account.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        account.setRole("USER");

        authAccountRepository.save(account);
    }

    // Login
    public AuthResponse login(LoginRequest request) {
        AuthAccount account = authAccountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), account.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }

        String accessToken = tokenService.generateToken(account.getEmail());
        String refreshToken = tokenService.generateRefreshToken(account.getEmail());

        return new AuthResponse(accessToken, refreshToken);
    }

    // Refresh Token
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (!tokenService.isValid(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String email = tokenService.extractEmail(refreshToken);
        String newAccessToken = tokenService.generateToken(email);
        String newRefreshToken = tokenService.generateRefreshToken(email); // tùy chọn: bạn có thể giữ lại token cũ

        return new AuthResponse(newAccessToken, newRefreshToken);
    }

    // Logout
    public void logout(String refreshToken) {
        if (!tokenService.isValid(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        tokenService.invalidateToken(refreshToken);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AuthAccount account = authAccountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(account.getEmail())
                .password(account.getPasswordHash())
                .roles(account.getRole())
                .build();
    }

}
