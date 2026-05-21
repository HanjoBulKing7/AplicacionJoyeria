package com.jewelry.managementsystem.controllers;

import com.jewelry.managementsystem.models.RefreshToken;
import com.jewelry.managementsystem.repositories.UserRepository;
import com.jewelry.managementsystem.security.jwt.JwtUtils;
import com.jewelry.managementsystem.security.request.LoginRequest;
import com.jewelry.managementsystem.security.request.RefreshTokenRequest;
import com.jewelry.managementsystem.security.request.SignUpRequest;
import com.jewelry.managementsystem.security.response.JWTResponse;
import com.jewelry.managementsystem.security.response.MessageResponse;
import com.jewelry.managementsystem.security.services.AuthService;
import com.jewelry.managementsystem.security.services.AuthServiceImpl;
import com.jewelry.managementsystem.security.services.RefreshTokenService;
import com.jewelry.managementsystem.security.services.RefreshTokenServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println("---- ENTRANDO AL SIGNIN CON: " + loginRequest.getUsername());
        JWTResponse response = authService.authenticateAndGetUserInfo(loginRequest);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> resgisterUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        MessageResponse response = authService.registerUser(signUpRequest);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        // request contiene el refresh token string que mandó el frontend
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)   // ¿expiró?
                .map(RefreshToken::getUser)                    // saca el User
                .map(user -> {
                    String newAccessToken = jwtUtils.generateAccessToken(userRepository.findById(user).get().getUsername(), userRepository.findById(user).get().getRoles());
                    return ResponseEntity.ok(new TokenRefreshResponse(newAccessToken, requestRefreshToken));
                })
                .orElseThrow(() -> new RuntimeException("Refresh token no encontrado"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signoutUser(){
        ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie(null);

        return ResponseEntity.ok( new MessageResponse("You've been signed out!"));
    }
}
