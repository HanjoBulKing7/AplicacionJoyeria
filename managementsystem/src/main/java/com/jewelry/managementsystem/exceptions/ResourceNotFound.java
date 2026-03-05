package com.jewelry.managementsystem.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;


public class ResourceNotFound extends RuntimeException {

    String resourceName;
    String field;
    String fieldValue;
    Long resourceId;

    ///  Thrown Exception when resource was not found by "field"
    public ResourceNotFound(String resourceName, String field, String fieldValue) {
        super(String.format(" %s with %s: %s not found", resourceName, field, fieldValue));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldValue = fieldValue;
    }

    ///  Exception thrown when not found by Id
    public ResourceNotFound(String resourceName, Long resourceId) {
        super(String.format("%s with id: %d not found", resourceName, resourceId));
        this.resourceName = resourceName;
        this.resourceId = resourceId;
    }

}
