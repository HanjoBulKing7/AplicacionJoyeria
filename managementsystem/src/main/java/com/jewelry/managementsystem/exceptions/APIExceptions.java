package com.jewelry.managementsystem.exceptions;

import lombok.NoArgsConstructor;


public class APIExceptions extends RuntimeException {

    public APIExceptions(){}

    public APIExceptions(String message){
        super( message);
    }
}
