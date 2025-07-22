package com.bridgelabz.user_service.exception;

import com.bridgelabz.user_service.controller.CustomerController;
import com.bridgelabz.user_service.dto.CustomerRequest;
import com.bridgelabz.user_service.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn404_whenCustomerNotFound() throws Exception {
        Mockito.when(customerService.findById(anyInt()))
                .thenThrow(new CustomerNotFoundException("Customer not found"));

        mockMvc.perform(get("/api/v1/customers/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Customer not found"));
    }

    @Test
    void shouldReturn400_whenValidationFails() throws Exception {
        CustomerRequest invalidRequest = new CustomerRequest(null, "Doe", "invalid-email", null);

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstname").value("Customer firstname is required"))
                .andExpect(jsonPath("$.email").value("Customer Email is not a valid email address"));
    }

    @Test
    void shouldReturn400_whenMalformedJson() throws Exception {
        String malformedJson = "{ \"firstname\": \"John\", \"lastname\": \"Doe\", "; // missing closing

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Malformed JSON request. Please check your request body."));
    }

    @Test
    void shouldReturn400_whenIllegalArgument() throws Exception {
        Mockito.when(customerService.existsById(anyInt()))
                .thenThrow(new IllegalArgumentException("Invalid ID"));

        mockMvc.perform(get("/api/v1/customers/exists/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid ID"));
    }

    @Test
    void shouldReturn500_whenGenericException() throws Exception {
        Mockito.when(customerService.findAllCustomers())
                .thenThrow(new RuntimeException("DB down"));

        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Something went wrong: DB down"));
    }
}
