package com.example.demo.exception;

public class CustomRuntimeException extends RuntimeException {
    private String message;

    public CustomRuntimeException(String message){
        super(message);
        this.message=message;
    }
}
