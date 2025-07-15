package com.learning.order.exception;

public class InvalidOrderOperationException extends RuntimeException {
    public InvalidOrderOperationException(String msg) { super(msg); }
}
