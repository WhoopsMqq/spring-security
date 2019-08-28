package com.whoops.core.validate;


import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {


    private static final long serialVersionUID = 2149977692176611332L;


    public ValidateCodeException(String message){
        super(message);
    }

}
