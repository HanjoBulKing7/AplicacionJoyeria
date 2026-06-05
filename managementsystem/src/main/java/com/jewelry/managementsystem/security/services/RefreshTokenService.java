package com.jewelry.managementsystem.security.services;

import com.jewelry.managementsystem.models.RefreshToken;

public interface RefreshTokenService {

    public RefreshToken createRefreshToken(Long userId);
    public RefreshToken verifyExpiration(RefreshToken token);
}
