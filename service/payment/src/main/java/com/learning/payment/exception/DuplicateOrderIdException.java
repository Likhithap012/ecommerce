package com.learning.payment.exception;

public class DuplicateOrderIdException extends RuntimeException {
    public DuplicateOrderIdException(String message) {
        super(message);
    }
}
