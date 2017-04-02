package com.gloomy.security;

import javassist.NotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2017 Gloomy
 * Created by Gloomy on 24-Mar-17.
 */
@ControllerAdvice
public class RestException {
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleCustomException(AccessDeniedException ex) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("status", false);
        metadata.put("code", 401);
        metadata.put("message", "Unauthorized: " + ex.getMessage());
        return new ResponseEntity<Object>(metadata, HttpStatus.OK);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<?> handleCustomException(AuthenticationCredentialsNotFoundException ex) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("status", false);
        metadata.put("code", 403);
        metadata.put("message", "Forbidden: " + ex.getMessage());
        return new ResponseEntity<Object>(metadata, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleCustomException(MethodArgumentNotValidException ex) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("status", false);
        metadata.put("code", 400);
        metadata.put("message", "Bad Request: " + ex.getMessage());
        return new ResponseEntity<Object>(metadata, HttpStatus.OK);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleCustomException(ConstraintViolationException ex) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("status", false);
        metadata.put("code", 422);
        metadata.put("message", "Unprocessable Entity: " + ex.getMessage());
        return new ResponseEntity<Object>(metadata, HttpStatus.OK);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<?> handleCustomException(EntityExistsException ex) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("status", false);
        metadata.put("code", 409);
        metadata.put("message", "Conflict: " + ex.getMessage());
        return new ResponseEntity<Object>(metadata, HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleCustomException(NotFoundException ex) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("status", false);
        metadata.put("code", 404);
        metadata.put("message", "Page Not Found: " + ex.getMessage());
        return new ResponseEntity<Object>(metadata, HttpStatus.OK);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleCustomException(EntityNotFoundException ex) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("status", false);
        metadata.put("code", 404);
        metadata.put("message", "Not Found Entity: " + ex.getMessage());
        return new ResponseEntity<Object>(metadata, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleCustomException(Exception ex) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("status", false);
        metadata.put("code", 500);
        metadata.put("message", "Internal Server Error: " + ex.getMessage());
        return new ResponseEntity<Object>(metadata, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
