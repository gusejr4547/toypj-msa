package com.example.catalogservice.Exception;

public class BusinessLogicException extends RuntimeException{
    public BusinessLogicException(String message){
        super(message);
    }
}
