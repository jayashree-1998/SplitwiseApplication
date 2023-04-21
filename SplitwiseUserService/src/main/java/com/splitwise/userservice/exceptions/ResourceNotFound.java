package com.splitwise.userservice.exceptions;

public class ResourceNotFound extends RuntimeException{
    String resourceName;
    String fieldName;
    String fieldValue;

    public ResourceNotFound(String resourceName,String fieldName,String fieldValue) {
        super(String.format("%s not found with %s: %s",resourceName,fieldName,fieldValue ));
        this.fieldName=fieldName;
        this.resourceName=resourceName;
        this.fieldValue=fieldValue;
    }
}
