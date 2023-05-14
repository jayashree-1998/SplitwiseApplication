package com.splitwise.userservice.exceptions;

import com.splitwise.userservice.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceNotFound extends RuntimeException{

    private static final Logger logger = LoggerFactory.getLogger(ResourceNotFound.class);
    String resourceName;
    String fieldName;
    public ResourceNotFound(String resourceName,String fieldName) {
        super(String.format("%s: %s not found",resourceName,fieldName ));
        logger.error("'{}' ${}$ #{}# *{}* #{}# - Resource not Found","","","","","");
        this.fieldName=fieldName;
        this.resourceName=resourceName;
    }
}
