package com.jewelry.managementsystem.security.response;

import lombok.Data;

import java.util.List;
@Data

public class UserInfoResponse {

    private Long id;
    private String username;
    private List<String> roles;

    public UserInfoResponse(Long id, String jwToken, String username, List<String> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    public UserInfoResponse(Long id, String username, List<String> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

}
