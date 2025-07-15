package com.bridglabz.cart.exceptions;

public class InvalidCartOperationException extends RuntimeException {
    public InvalidCartOperationException(String message) {
        super(message);
    }
}
