package com.sfeir.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class TodoExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .message(ex.getMessage())
                        .status("NOT_FOUND")
                        .build()
        , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ExceptionResponse.builder()
                .message("Validation for inputs")
                .status("BAD_REQUEST")
                .details(
                        ex.getBindingResult().getFieldErrors().stream().map(
                                DefaultMessageSourceResolvable::getDefaultMessage
                        ).collect(Collectors.toList()))
                .build()
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionResponse> handleNullPointerException(NullPointerException ex) {
        return new ResponseEntity<>(
                ExceptionResponse.builder()
                        .message(ex.getMessage())
                        .status("BAD_REQUEST")
                        .build()
        , HttpStatus.BAD_REQUEST);
    }


}
