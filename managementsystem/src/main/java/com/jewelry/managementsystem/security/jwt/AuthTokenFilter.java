package com.jewelry.managementsystem.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;/// Utilities from the class
    private final UserDetailsService userDetailsService; /// User details service

    private static final Logger logger =  LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override ///  Method that filters each request
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        logger.info("AuthTokenFilter called for URI: " + request.getRequestURI());


        try{
            String jwt = parseJwt(request);/// Transform the Jwt
            if(jwt!=null && jwtUtils.validateJWT(jwt)){ ///  If exists go ahead
                String username = jwtUtils.getUsernameFromJWT(jwt); /// Get username from cookie
                UserDetails userDetails = userDetailsService.loadUserByUsername(username); /// Load the registered user by username
                UsernamePasswordAuthenticationToken authentication = ///  Create new auth object with null because JWT already validated the user
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                /// Get details from the current user
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                ///  Set the user on global context so any part of the app will know the user is authenticated
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("Roles from JWT: {}", userDetails.getAuthorities());
            }
        }catch(Exception e){
            logger.error("JWT not valid for this request");
        }

        filterChain.doFilter(request, response); /// Call the next filter
    }

    private String parseJwt(HttpServletRequest request) {

        String jwt = jwtUtils.getJwtFromCookies(request);

        logger.debug("AuthTokenFilter jwt received: " + jwt);

        return jwt;
    }
}
