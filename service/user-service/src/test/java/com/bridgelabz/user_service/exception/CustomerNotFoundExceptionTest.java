package com.bridgelabz.user_service.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerNotFoundExceptionTest {

    @Test
    void testWithValidMessage() {
        String message = "Customer with ID 123 not found";
        CustomerNotFoundException exception = new CustomerNotFoundException(message);
        assertEquals(message, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testWithNullMessage() {
        CustomerNotFoundException exception = new CustomerNotFoundException(null);
        assertNull(exception.getMessage());
    }

    @Test
    void testWithEmptyMessage() {
        CustomerNotFoundException exception = new CustomerNotFoundException("");
        assertEquals("", exception.getMessage());
    }

    @Test
    void testWithWhitespaceMessage() {
        String message = "   ";
        CustomerNotFoundException exception = new CustomerNotFoundException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testThrowAndCatchCustomerNotFoundException() {
        try {
            throw new CustomerNotFoundException("Customer not found");
        } catch (CustomerNotFoundException exception) {
            assertEquals("Customer not found", exception.getMessage());
        }
    }
}
