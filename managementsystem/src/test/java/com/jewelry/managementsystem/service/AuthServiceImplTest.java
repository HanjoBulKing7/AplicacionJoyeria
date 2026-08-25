package com.jewelry.managementsystem.service;

import com.jewelry.managementsystem.models.Role;
import com.jewelry.managementsystem.models.Roles;
import com.jewelry.managementsystem.models.User;
import com.jewelry.managementsystem.repositories.RoleRepository;
import com.jewelry.managementsystem.repositories.UserRepository;
import com.jewelry.managementsystem.security.jwt.JwtUtils;
import com.jewelry.managementsystem.security.request.LoginRequest;
import com.jewelry.managementsystem.security.request.SignUpRequest;
import com.jewelry.managementsystem.security.response.JWTResponse;
import com.jewelry.managementsystem.security.response.MessageResponse;
import com.jewelry.managementsystem.security.services.AuthServiceImpl;
import com.jewelry.managementsystem.security.services.UserDetailsImpl;
import com.jewelry.managementsystem.security.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Disabled
@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    ///  Dependency that AuthServiceImpl uses
    private AuthenticationManager authenticationManager;

    @Mock
    /// To register the user
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserDetailsServiceImpl userDetailsService;


    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    ///  Inject the clase that I want to test
    private AuthServiceImpl authService;
    @Mock
    private PasswordEncoder passwordEncoder;

    private LoginRequest testLoginRequest; /// Dummy login request
    private SignUpRequest  testSignUpRequest; /// Dummy signup request


    @BeforeEach
    public void setUp() {
        ///  Setting up a fake login request
        testLoginRequest = new LoginRequest();
        testLoginRequest.setUsername("johan_dev");
        testLoginRequest.setPassword("password123");

        ///  Sign up request
        testSignUpRequest = new SignUpRequest();
        testSignUpRequest.setUsername("johan_dev");
        testSignUpRequest.setEmail("test@domain.com");
        testSignUpRequest.setPassword("password123");
        testSignUpRequest.setRoles(new HashSet<>());
    }

    @Test
    @DisplayName("Log In - Happy path: Login user and generate JWT ")
    void authenticateAndGetUserInfo_Success(){
        ///  Setting the mock behavior
        Authentication auth = mock(Authentication.class);
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "johan_dev", "email@test.com", "pass", List.of());

        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(userDetails);
        JWTResponse response = authService.authenticateAndGetUserInfo(testLoginRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(testLoginRequest.getUsername(), response.getUsername());

        verify(authenticationManager, times(1)).authenticate(any());

    }


    @Test
    @DisplayName("Register user")
    void registerUser_succes(){

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty()); /// If user with the same username exists
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        Role userRole = new Role(Roles.USER);

        when(roleRepository.findByRolename(Roles.USER)).thenReturn(Optional.of(userRole)); ///It should return the existing role if exists

        when(passwordEncoder.encode(anyString())).thenReturn("password123"); /// It must return something either way "Null pointer exception"

        MessageResponse response = authService.registerUser(testSignUpRequest); /// Save response

        ///  If response match with the hardcoded text
        Assertions.assertEquals("User registered successfully!", response.getMessage());

        ///  If the user was saved all code above was executed
        verify(userRepository, times(1)).save(any(User.class));
    }

}
