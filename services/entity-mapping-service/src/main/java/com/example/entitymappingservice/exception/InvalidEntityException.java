package com.example.entitymappingservice.exception;

public class InvalidEntityException extends RuntimeException{
    public InvalidEntityException(String message) {
        super(message);
    }
}
