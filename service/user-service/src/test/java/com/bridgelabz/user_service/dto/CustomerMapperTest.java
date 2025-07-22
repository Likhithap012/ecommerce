package com.bridgelabz.user_service.dto;

import com.bridgelabz.user_service.entity.Address;
import com.bridgelabz.user_service.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    private CustomerMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new CustomerMapper();
    }

    @Test
    void toCustomer_shouldMapCorrectly() {
        Address address = new Address(1, "Main St", "123", "400001");
        CustomerRequest request = new CustomerRequest("Likhitha", "Pulluru", "likhitha@gmail.com", address);

        Customer customer = mapper.toCustomer(request);

        assertNotNull(customer);
        assertEquals("Likhitha", customer.getFirstname());
        assertEquals("Pulluru", customer.getLastname());
        assertEquals("likhitha@gmail.com", customer.getEmail());
        assertEquals(address, customer.getAddress());
    }

    @Test
    void fromCustomer_shouldMapCorrectly() {
        Address address = new Address(1, "Main St", "123", "400001");
        Customer customer = Customer.builder()
                .id(101)
                .firstname("Likhitha")
                .lastname("Pulluru")
                .email("likhitha@gmail.com")
                .address(address)
                .build();

        CustomerResponse response = mapper.fromCustomer(customer);

        assertNotNull(response);
        assertEquals(101, response.id());
        assertEquals("Likhitha", response.firstname());
        assertEquals("Pulluru", response.lastname());
        assertEquals("likhitha@gmail.com", response.email());
        assertEquals(address, response.address());
    }

    @Test
    void toCustomer_withNull_shouldReturnNull() {
        assertNull(mapper.toCustomer(null));
    }

    @Test
    void fromCustomer_withNull_shouldReturnNull() {
        assertNull(mapper.fromCustomer(null));
    }
}
