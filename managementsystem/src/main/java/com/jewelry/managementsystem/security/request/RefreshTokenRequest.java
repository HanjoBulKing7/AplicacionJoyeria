package com.jewelry.managementsystem.security.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {

    @NotBlank
    String refreshToken;

}
