package com.jewelry.managementsystem.exceptions;


import lombok.Data;

@Data
public class TokenException extends RuntimeException{
    private String token;
    private String error;

    public TokenException(String token, String error) {
        this.token = token;
        this.error = error;
    }

    public TokenException(String error){
        this.error = error;
    }
}
