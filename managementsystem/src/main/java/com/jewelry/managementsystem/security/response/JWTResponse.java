package com.jewelry.managementsystem.security.response;

import com.jewelry.managementsystem.models.RefreshToken;
import lombok.Data;
import java.util.List;
@Data
public class JWTResponse {

    private Long id;
    private String username;
    private List<String> roles;
    private String accessToken;
    private String refreshToken;

    public JWTResponse(Long id, String accessToken, String refreshToken, String username, List<String> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public JWTResponse(Long id, String username, List<String> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

}
