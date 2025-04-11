package com.scm.helpers;

import java.lang.*; 


public class ResourceNotFoundException extends RuntimeException { 

    public ResourceNotFoundException(String message) {

        super(message) ;
    }
    public ResourceNotFoundException() {

        super("Resource not found") ;
    }


}
