package com.jewelry.managementsystem.controllers;

import com.jewelry.managementsystem.security.jwt.JwtUtils;
import com.jewelry.managementsystem.security.request.LoginRequest;
import com.jewelry.managementsystem.security.request.SignUpRequest;
import com.jewelry.managementsystem.security.response.MessageResponse;
import com.jewelry.managementsystem.security.response.UserInfoResponse;
import com.jewelry.managementsystem.security.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Endpoints for user registration, login, and logout using JWT Cookies")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @Operation(summary = "Login User", description = "Authenticates user credentials and returns user info with a JWT Set-Cookie header.")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        UserInfoResponse response = authService.authenticateAndGetUserInfo(loginRequest);
        ResponseCookie jwtCookie = authService.generateJwtCookieForuser(loginRequest.getUsername());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(response);
    }

    @Operation(summary = "Register User", description = "Creates a new user account with a default 'USER' role.")
    @PostMapping("/signup")
    public ResponseEntity<?> resgisterUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        MessageResponse response = authService.registerUser(signUpRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Logout User", description = "Clears the session by returning an expired JWT Cookie.")
    @PostMapping("/signout")
    public ResponseEntity<?> signoutUser(){
        ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie(null);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}