package com.jewelry.managementsystem.exceptions;

import lombok.Data;

@Data
public class EmptyResourceException extends RuntimeException {

    private Long id;
    private String resourceName;
    private String field;
    private String fieldValue;
    public EmptyResourceException() {
        super("No resources found");
    }

    public EmptyResourceException(String resourceName) {
        super("No "+resourceName+" found" );
        this.resourceName = resourceName;

    }

    public EmptyResourceException(Long id, String resourceName ) {
        super("No "+resourceName+" with id: "+id+" found");
        this.id = id;
        this.resourceName = resourceName;
    }

    public EmptyResourceException(String resourceName, String field, String fieldValue) {
        super("No "+resourceName+" with "+field+": "+fieldValue+" found");
        this.resourceName = resourceName;
        this.field = field;
        this.fieldValue = fieldValue;
    }
}
