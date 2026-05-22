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
import org.springframework.http.HttpStatus;
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

        JWTResponse refreshTokenResponse = authService.refreshToken(request.getRefreshToken());

        return new ResponseEntity<>(refreshTokenResponse, HttpStatus.OK);
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signoutUser(){
        ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie(null);

        return ResponseEntity.ok( new MessageResponse("You've been signed out!"));
    }
}
