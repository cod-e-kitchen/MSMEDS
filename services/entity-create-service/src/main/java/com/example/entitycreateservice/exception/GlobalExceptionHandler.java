package com.example.entitycreateservice.exception;

import com.example.entitycreateservice.dto.EntityErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ResponseEntity<EntityErrorResponse> handleArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException ex) {
        EntityErrorResponse entityErrorResponse = new EntityErrorResponse(LocalDateTime.now(),ex.getMessage(),"Search for the entity failed");
        return new ResponseEntity<>(entityErrorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<?> handleEntityExistsException(EntityAlreadyExistsException e) {
        EntityErrorResponse entityExists = new EntityErrorResponse(LocalDateTime.now(),
               e.getMessage(),"Entity already Exists");
        return new ResponseEntity<>(entityExists, HttpStatus.CONFLICT);
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

    @ExceptionHandler(EntityValidationException.class)
    public ResponseEntity<EntityErrorResponse> handleEntityValidationException(EntityValidationException ex) {
        EntityErrorResponse entityErrorResponse = new EntityErrorResponse(LocalDateTime.now(),
                ex.getMessage(),"Invalid Entity Creation");
        return new ResponseEntity<>(entityErrorResponse,HttpStatus.BAD_REQUEST);
    }


}
