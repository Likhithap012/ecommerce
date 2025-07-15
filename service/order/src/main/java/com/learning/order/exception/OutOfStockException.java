package com.learning.order.exception;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String msg) { super(msg); }
}
