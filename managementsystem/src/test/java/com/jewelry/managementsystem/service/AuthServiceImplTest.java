package com.jewelry.managementsystem.service;

import com.jewelry.managementsystem.models.Role;
import com.jewelry.managementsystem.models.Roles;
import com.jewelry.managementsystem.models.User;
import com.jewelry.managementsystem.repositories.RoleRepository;
import com.jewelry.managementsystem.repositories.UserRepository;
import com.jewelry.managementsystem.security.jwt.JwtUtils;
import com.jewelry.managementsystem.security.request.LoginRequest;
import com.jewelry.managementsystem.security.request.SignUpRequest;
import com.jewelry.managementsystem.security.response.MessageResponse;
import com.jewelry.managementsystem.security.response.UserInfoResponse;
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
    public void setUp() { ///  Setting up a fake login request
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
    @DisplayName("Display the user information once authorized")
    void authenticateAndGetUserInfo_Success(){
        ///  Setting the mock behavior
        Authentication auth = mock(Authentication.class);
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "johan_dev", "email@test.com", "pass", List.of());

        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(userDetails);
        UserInfoResponse  response = authService.authenticateAndGetUserInfo(testLoginRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("johan_dev", response.getUsername());
        Assertions.assertEquals(1L, response.getId());

        verify(authenticationManager, times(1)).authenticate(any());

    }


    @Test
    @DisplayName("Check if the JWT was successfully generated")
    void generateJwt_Success(){
        String username = "johan_dev";
        UserDetailsImpl mockDetails = new UserDetailsImpl(1l, username, "email@test.com", "pass", List.of());
        ResponseCookie mockCookie = ResponseCookie.from("jewelryC0ok31","fake-jwt-token").build();

        when(userDetailsService.loadUserByUsername(any())).thenReturn(mockDetails);
        when(jwtUtils.generateJwtCookie(any())).thenReturn(mockCookie);

        ResponseCookie result = authService.generateJwtCookieForuser(username);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("jewelryC0ok31", result.getName());
        Assertions.assertEquals("fake-jwt-token", result.getValue());

        verify(userDetailsService, times(1)).loadUserByUsername(any());
        verify(jwtUtils, times(1)).generateJwtCookie(any());

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
