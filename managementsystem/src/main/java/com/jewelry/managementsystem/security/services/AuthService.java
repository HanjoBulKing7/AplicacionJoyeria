package com.jewelry.managementsystem.security.services;

import com.jewelry.managementsystem.security.request.LoginRequest;
import com.jewelry.managementsystem.security.request.RefreshTokenRequest;
import com.jewelry.managementsystem.security.request.SignUpRequest;
import com.jewelry.managementsystem.security.response.JWTResponse;
import com.jewelry.managementsystem.security.response.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {

    JWTResponse authenticateAndGetUserInfo(LoginRequest loginRequest);
    MessageResponse registerUser( SignUpRequest signUpRequest);
    JWTResponse refreshToken(String refreshToken);

    String logoutUser( RefreshTokenRequest refreshTokenRequest);
}
