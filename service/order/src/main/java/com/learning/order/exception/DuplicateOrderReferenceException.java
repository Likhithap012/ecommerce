package com.learning.order.exception;

public class DuplicateOrderReferenceException extends RuntimeException {
    public DuplicateOrderReferenceException(String message) {
        super(message);
    }
}
