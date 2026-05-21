package com.jewelry.managementsystem.security.services;

import com.jewelry.managementsystem.models.RefreshToken;
import com.jewelry.managementsystem.repositories.RefreshTokenRepository;
import com.jewelry.managementsystem.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${spring.app.refreshExpirationMs}")
    private Long refreshExpirationMs;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken token = new RefreshToken();
        token.setUser(userRepository.findById(userId).orElseThrow());
        token.setToken(UUID.randomUUID().toString());
        token.setExpirationDate(Instant.now().plusMillis(refreshExpirationMs));

        return refreshTokenRepository.save(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpirationDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired, login again please!");
        }
        return token;
    }

}
