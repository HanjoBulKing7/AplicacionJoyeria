package com.jewelry.managementsystem.exceptions;

public class EmptyResourceException extends RuntimeException {

    private Long id;
    private String resourceName;

    public EmptyResourceException() {
        super("No resources found");
    }

    public EmptyResourceException(String resourceName) {
        super("No "+resourceName+" found" );
        this.resourceName = resourceName;

    }

    public EmptyResourceException(Long id, String resourceName ) {
        super("No "+resourceName+" with id:"+id+" found");
    }
}
