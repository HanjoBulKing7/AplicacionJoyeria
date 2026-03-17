package com.jewelry.managementsystem.security.services;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserRepository  userRepository;
    @Autowired
    RoleRepository roleRepository;

    PasswordEncoder passwordEncoder;

    @Override
    public UserInfoResponse authenticateAndGetUserInfo(LoginRequest loginRequest) {
            ///  Try to authenticate if fails the enry point throws an exception
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        ///  If the authentication was succesfully performed
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ///  Get user details
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ///  Get the roles a list as the DTO has
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        ///  Return data and the controller will handle cookies
        return new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), roles);
    }

    @Override
    public ResponseCookie generateJwtCookieForuser(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtUtils.generateJwtCookie(userDetails);
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
        user.setRoles(roles);
        userRepository.save(user);

        return new MessageResponse("User registered successfully!");

    }

}
