package com.example.entitymappingservice.exception;

public class MappingRequestAlreadyExistsException extends RuntimeException {
    public MappingRequestAlreadyExistsException(String message) {
        super(message);
    }
}
