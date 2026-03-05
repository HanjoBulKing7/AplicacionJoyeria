package com.jewelry.managementsystem.exceptions;

public class DuplicateResourceException extends RuntimeException {

    String resourceName;
    String resourceField;
    String fieldValue;

    public DuplicateResourceException(String resourceName, String resourceField, String fieldValue) {
        super(String.format("%s with %s: %s already exists",  resourceName, resourceField, fieldValue));
        this.resourceName = resourceName;
        this.resourceField = resourceField;
        this.fieldValue = fieldValue;
    }
}
