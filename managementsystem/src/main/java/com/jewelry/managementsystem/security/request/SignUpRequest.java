package com.jewelry.managementsystem.security.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpRequest {

    @NotBlank
    @Size( min=3, max=20)
    public String username;

    @NotBlank
    @Size( max = 50)
    @Email
    public String email;

    private Set<String> roles;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
