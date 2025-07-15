package com.learning.payment.exception;

import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFound(CustomerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found: " + ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<String> handle(EntityNotFoundException exp) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(exp.getMessage());
  }
    @ExceptionHandler(DuplicateOrderIdException.class)
    public ResponseEntity<String> handle(DuplicateOrderIdException exp) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exp.getMessage());
    }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
    var errors = new HashMap<String, String>();
    exp.getBindingResult().getAllErrors()
            .forEach(error -> {
              var fieldName = ((FieldError) error).getField();
              var errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });

    return ResponseEntity
            .status(BAD_REQUEST)
            .body(new ErrorResponse(errors));
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<String> handle(BusinessException exp) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(exp.getMsg());
  }
    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<String> handleFeignNotFound(FeignException.NotFound ex) {
        if (ex.request().url().contains("/customers/")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer not found.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found.");
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignGeneral(FeignException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body("Remote service error: " + ex.getMessage());
    }

}
