package com.jewelry.managementsystem.security.services;

import com.jewelry.managementsystem.exceptions.TokenException;
import com.jewelry.managementsystem.models.RefreshToken;
import com.jewelry.managementsystem.models.Role;
import com.jewelry.managementsystem.models.Roles;
import com.jewelry.managementsystem.models.User;
import com.jewelry.managementsystem.repositories.RefreshTokenRepository;
import com.jewelry.managementsystem.repositories.RoleRepository;
import com.jewelry.managementsystem.repositories.UserRepository;
import com.jewelry.managementsystem.security.jwt.JwtUtils;
import com.jewelry.managementsystem.security.request.LoginRequest;
import com.jewelry.managementsystem.security.request.RefreshTokenRequest;
import com.jewelry.managementsystem.security.request.SignUpRequest;
import com.jewelry.managementsystem.security.response.JWTResponse;
import com.jewelry.managementsystem.security.response.MessageResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository  userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public JWTResponse authenticateAndGetUserInfo(LoginRequest loginRequest) { // Login
            ///  Try to authenticate if fails the enry point throws an exception
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        ///  If the authentication was successfully performed
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ///  Get user details
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ///  Get the roles a list as the DTO has
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String accessToken = jwtUtils.generateAccessToken(userDetails.getUsername(), roles);
        RefreshToken refreshToken = refreshTokenRepository.findByUser_UserId(userDetails.getId())
                .orElseThrow(()-> new TokenException("Invalid token for logging in"));
        ///  Return data and the controller will handle cookies
        return new JWTResponse(  accessToken, refreshToken.getToken(), userDetails.getUsername(), roles);
    }

    @Override
    public MessageResponse registerUser(SignUpRequest signUpRequest) {
        if(userRepository.findByUsername(signUpRequest.getUsername()).isPresent())
            throw new RuntimeException("Error: Username is already taken!");

        if(userRepository.existsByEmail(signUpRequest.getEmail()))
            throw new RuntimeException("Error: Email is already taken!");

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if( strRoles.isEmpty() ){
            Role userRole = roleRepository.findByRolename(Roles.USER)
                    .orElseThrow( ()-> new RuntimeException("Error: Role not found "));

            roles.add(userRole);
        }else{
            strRoles.forEach(role -> {
                Roles roleEnum = switch (role) {
                    case "admin" -> Roles.ADMIN;
                    default -> Roles.USER;
                };

                Role finalRole = roleRepository.findByRolename(roleEnum)
                        .orElseThrow(() -> new RuntimeException("Error: Role " + roleEnum + " not found"));
                roles.add(finalRole);
            });
        }

        RefreshToken refreshTokenForUser = new RefreshToken();
        refreshTokenForUser.setUser(user);
        refreshTokenForUser.setToken(jwtUtils.generateRefreshToken());

        Instant expirationTime = Instant.now().plus(7, ChronoUnit.DAYS); /// Generate the expiration time

        refreshTokenForUser.setExpirationDate(expirationTime);

        user.setRoles(roles);
        userRepository.save(user);
        refreshTokenRepository.save(refreshTokenForUser);

        return new MessageResponse("User registered successfully!");

    }

    @Override
    public JWTResponse refreshToken(String refreshToken) {

        //Check if the refresh token exists
        RefreshToken refreshTokenFromUser = refreshTokenRepository.findByToken(refreshToken)
                ///  IN case the token does not exist
                .orElseThrow(() -> new RuntimeException("Error: Refresh token not found to refresh!"));new TokenException(refreshToken, "Invalid token");

        ///  In case the toke does exist
        if( refreshTokenFromUser.getExpirationDate().isBefore(Instant.now()) )
            throw new TokenException("Token is already expired!");

        refreshTokenFromUser.setToken("");
        String newToken = jwtUtils.generateRefreshToken();
        refreshTokenFromUser.setToken(newToken);
        refreshTokenFromUser.setExpirationDate(Instant.now().plus(7, ChronoUnit.DAYS));
        refreshTokenRepository.save(refreshTokenFromUser);

        JWTResponse refreshedResponse = new JWTResponse();
        User userFromRefreshToken = refreshTokenFromUser.getUser();

        refreshedResponse.setRefreshToken(refreshTokenFromUser.getToken());
        List<String> stringRoles = userFromRefreshToken.getRoles().stream()
                        .map(role -> role.getRolename().toString())
                                .toList();

        refreshedResponse.setAccessToken(jwtUtils.generateAccessToken(userFromRefreshToken.getUsername(), stringRoles));
        refreshedResponse.setRoles(stringRoles);
        refreshedResponse.setRefreshToken(newToken);
        refreshedResponse.setUsername(userFromRefreshToken.getUsername());

        return refreshedResponse;

    }

    @Override
    @Transactional
    public String logoutUser(RefreshTokenRequest refreshTokenRequest) {
        String issuedToken = refreshTokenRequest.getRefreshToken();
        Integer rowsAffected = refreshTokenRepository.deleteByToken(issuedToken);

        if(rowsAffected == 0)
            throw new TokenException("Token not found");

        return "Logged out successfully";
    }
}