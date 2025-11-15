package org.example.userservice.exception;

import org.example.userservice.dto.ErrorResponseDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorResponseDTO handleException(DataIntegrityViolationException exception) {
        return ErrorResponseDTO.builder()
                .message("Username already exists")
                .status(HttpStatus.CONFLICT.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponseDTO handleException(ResourceNotFoundException exception) {
        return ErrorResponseDTO.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
