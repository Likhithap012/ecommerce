package com.brodgelabz.user_service.controller;

import com.brodgelabz.user_service.dto.CustomerRequest;
import com.brodgelabz.user_service.dto.CustomerResponse;
import com.brodgelabz.user_service.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCustomer_shouldReturnCustomerId() throws Exception {
        CustomerRequest request = new CustomerRequest("John", "Doe", "john.doe@example.com", null);

        when(customerService.createCustomer(any(CustomerRequest.class))).thenReturn(1);

        mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void updateCustomer_shouldReturnSuccessMessage() throws Exception {
        CustomerRequest request = new CustomerRequest("John", "Doe", "john.doe@example.com", null);

        doNothing().when(customerService).updateCustomer(Mockito.eq(1), any(CustomerRequest.class));

        mockMvc.perform(put("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer updated successfully."));
    }

    @Test
    void findAll_shouldReturnCustomerList() throws Exception {
        CustomerResponse response = new CustomerResponse(1, "John", "Doe", "john.doe@example.com", null);

        when(customerService.findAllCustomers()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));
    }

    @Test
    void existsById_shouldReturnTrue() throws Exception {
        when(customerService.existsById(1)).thenReturn(true);

        mockMvc.perform(get("/api/v1/customers/exists/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void findById_shouldReturnCustomer() throws Exception {
        CustomerResponse response = new CustomerResponse(1, "John", "Doe", "john.doe@example.com", null);

        when(customerService.findById(1)).thenReturn(response);

        mockMvc.perform(get("/api/v1/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void deleteCustomer_shouldReturnSuccessMessage() throws Exception {
        doNothing().when(customerService).deleteCustomer(1);

        mockMvc.perform(delete("/api/v1/customers/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer deleted successfully."));
    }
}
