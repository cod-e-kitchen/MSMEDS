package com.example.entitymappingservice.exception;

public class MappingAlreadyExistsException extends RuntimeException {
    public MappingAlreadyExistsException(String message) {
        super(message);
    }
}
