package com.jewelry.managementsystem.security.services;

import com.jewelry.managementsystem.security.request.LoginRequest;
import com.jewelry.managementsystem.security.request.SignUpRequest;
import com.jewelry.managementsystem.security.response.MessageResponse;
import com.jewelry.managementsystem.security.response.UserInfoResponse;
import org.springframework.http.ResponseCookie;

public interface AuthService {


   UserInfoResponse authenticateAndGetUserInfo( LoginRequest loginRequest);
   ResponseCookie generateJwtCookieForuser(String username);
    MessageResponse registerUser( SignUpRequest signUpRequest);
}
