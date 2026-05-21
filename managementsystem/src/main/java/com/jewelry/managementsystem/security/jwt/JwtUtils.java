package com.jewelry.managementsystem.security.jwt;

import com.jewelry.managementsystem.models.Roles;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class JwtUtils {

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;
    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${spring.jewelry.app.jwtCookieName}")
    private String jwtCookie;

    ///  Delete the cookie to sign out and obligate the user to sign up again
    public ResponseCookie getCleanJwtCookie(UserDetails userPrincipal){
        return ResponseCookie.from(jwtCookie, null)
                .path("/api")
                .build();
    }

    ///  Generating access token from Username
    public String generateAccessToken(String username, List<String> rolesList){
        return Jwts.builder()
                .claim("roles", rolesList)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date((new Date().getTime() + jwtExpirationMs)))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    ///  Generate refresh token
    public String generateRefreshToken ( ){
        return UUID.randomUUID().toString();
    }
    ///  Getting username from JWT token
    public String getUsernameFromJWT(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }


    ///  Generate signing key
    public Key getKey(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret));
    }
    ///  Validate JWT
    public boolean validateJWT(String authToken){
        try {
            System.out.println("Validate");
            Jwts.parser()
                    .verifyWith((SecretKey) getKey())
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        }catch(MalformedJwtException e){
            logger.error("Invalid JWT token: {}", e.getMessage());
        }catch(ExpiredJwtException e){
            logger.error("Expired JWT token: {}", e.getMessage());
        }catch(UnsupportedJwtException e){
            logger.error("Unsupported JWT token: {}", e.getMessage());
        }catch(IllegalArgumentException e){
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
