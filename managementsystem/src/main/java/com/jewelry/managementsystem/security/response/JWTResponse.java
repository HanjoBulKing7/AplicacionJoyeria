package com.jewelry.managementsystem.security.response;

import com.jewelry.managementsystem.models.RefreshToken;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class JWTResponse {

    private String username;
    private String email;
    private List<String> roles;
    private String accessToken;
    private String refreshToken;

    public JWTResponse( String accessToken, String refreshToken, String username, String email, List<String> roles) {
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
