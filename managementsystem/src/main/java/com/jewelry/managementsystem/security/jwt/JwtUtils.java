package com.jewelry.managementsystem.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
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
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;
    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${spring.jewelry.app.jwtCookieName}")
    private String jwtCookie;

    //    ///  Getting JWT from header
//    public String getJwtFromHeader(HttpServletRequest request){
//        String bearerToken = request.getHeader("Authorization");
//        logger.debug("Authorization header: {}", bearerToken);
//        if( bearerToken != null && bearerToken.startsWith("Bearer "))
//            return bearerToken.substring(7);
//
//        return null;
//    }
    ///  Delete the cookie to sign out and obligate the user to sign up again
    public ResponseCookie getCleanJwtCookie(UserDetails userPrincipal){
        return ResponseCookie.from(jwtCookie, null)
                .path("/api")
                .build();
    }
    ///  Get Jwt from the cookie
    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);/// Get the corresponding cookie as named on the app.properties

        if(cookie != null){
            logger.info("Found cookie: "+cookie.getValue());
            return cookie.getValue();/// Return the cookie if found
        }else
            return null;
    }

    ///  Generate the JWT cookie as response
    public ResponseCookie generateJwtCookie(UserDetails userPrincipal){

        String jwt = generateTokenFromUsername(userPrincipal.getUsername());

        return ResponseCookie.from(jwtCookie, jwt)
                .path("/api")///    Just use this on requests starting withg that prefix
                .maxAge(24*60*60) ///  Lifetime of the cookie ( 1 day )
                .httpOnly(true) /// Just server can read the cookie, not JS scripts nor XSS virus
                .build();
    }



    ///  Generating Token from Username
    public String generateTokenFromUsername( String username ){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration( new Date (( new Date().getTime() + jwtExpirationMs)))
                .signWith((SecretKey) getKey())
                .compact();


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
                    .parseClaimsJws(authToken);
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
