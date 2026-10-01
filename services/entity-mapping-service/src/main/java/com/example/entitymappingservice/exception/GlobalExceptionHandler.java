package com.example.entitymappingservice.exception;

import com.example.entitymappingservice.dto.EntityErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ResponseEntity<EntityErrorResponse> handleArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException ex) {
        EntityErrorResponse entityErrorResponse = new EntityErrorResponse(LocalDateTime.now(),ex.getMessage(),"Search for the entity failed");
        return new ResponseEntity<>(entityErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<EntityErrorResponse> handleException(Exception ex) {
        EntityErrorResponse entityErrorResponse = new EntityErrorResponse(LocalDateTime.now(),ex.getMessage(),"Unexpected Error");
        return new ResponseEntity<>(entityErrorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<EntityErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        EntityErrorResponse entityErrorResponse = new EntityErrorResponse(LocalDateTime.now(),ex.getMessage(),"Illegal Argument");
        return new ResponseEntity<>(entityErrorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<EntityErrorResponse> handleNullPointerException(NullPointerException ex) {
        EntityErrorResponse entityErrorResponse = new EntityErrorResponse(LocalDateTime.now(),ex.getMessage(),"Null Pointer");
        return new ResponseEntity<>(entityErrorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MappingAlreadyExistsException.class)
    public ResponseEntity<EntityErrorResponse> handleMappingAlreadyExistsException(MappingAlreadyExistsException ex) {
        EntityErrorResponse entityErrorResponse = new EntityErrorResponse(LocalDateTime.now(),ex.getMessage(),"Duplicate Mapping");
        return new ResponseEntity<>(entityErrorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MappingRequestAlreadyExistsException.class)
    public ResponseEntity<EntityErrorResponse> handleMappingRequestAlreadyExistsException(MappingRequestAlreadyExistsException ex) {
        EntityErrorResponse entityErrorResponse = new EntityErrorResponse(LocalDateTime.now(),ex.getMessage(),"Duplicate Mapping request");
        return new ResponseEntity<>(entityErrorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<EntityErrorResponse> handleInvalidEntityException(InvalidEntityException ex) {
        EntityErrorResponse entityErrorResponse = new EntityErrorResponse(LocalDateTime.now(),ex.getMessage(),"Invalid Entity requested for mapping");
        return new ResponseEntity<>(entityErrorResponse,HttpStatus.BAD_REQUEST);
    }

}
