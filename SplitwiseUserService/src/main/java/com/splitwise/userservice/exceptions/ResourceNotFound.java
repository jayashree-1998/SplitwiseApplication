package com.splitwise.userservice.exceptions;

public class ResourceNotFound extends RuntimeException{
    String resourceName;
    String fieldName;
    public ResourceNotFound(String resourceName,String fieldName) {
        super(String.format("%s: %s not found",resourceName,fieldName ));
        this.fieldName=fieldName;
        this.resourceName=resourceName;
    }
}
