package com.jewelry.managementsystem.exceptions;

public class EmptyResourceException extends RuntimeException {


    public EmptyResourceException() {
        super("No resources found");
    }

    public EmptyResourceException(String resourceName) {
        super("No "+resourceName+" found" );
    }

}
