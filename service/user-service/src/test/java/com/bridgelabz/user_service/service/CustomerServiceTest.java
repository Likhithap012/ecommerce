package com.bridgelabz.user_service.service;

import com.bridgelabz.user_service.dto.CustomerMapper;
import com.bridgelabz.user_service.dto.CustomerRequest;
import com.bridgelabz.user_service.repository.CustomerRepository;
import com.bridgelabz.user_service.dto.CustomerResponse;
import com.bridgelabz.user_service.entity.Address;
import com.bridgelabz.user_service.entity.Customer;
import com.bridgelabz.user_service.exception.CustomerNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private CustomerMapper mapper;

    @InjectMocks
    private CustomerService service;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer_shouldReturnCustomerId() {
        CustomerRequest request = new CustomerRequest("John", "Doe", "john@example.com", null);
        Customer customerEntity = Customer.builder().id(1).firstname("John").lastname("Doe").email("john@example.com").build();

        when(mapper.toCustomer(request)).thenReturn(customerEntity);
        when(repository.save(customerEntity)).thenReturn(customerEntity);

        Integer result = service.createCustomer(request);

        assertEquals(1, result);
        verify(repository).save(customerEntity);
    }

    @Test
    void updateCustomer_shouldUpdateFields() {
        Customer existing = Customer.builder().id(1).firstname("Old").email("old@example.com").build();
        Address address = new Address(1, "Street", "123", "400001");
        CustomerRequest update = new CustomerRequest("New", "Doe", "new@example.com", address);

        when(repository.findById(1)).thenReturn(Optional.of(existing));

        service.updateCustomer(1, update);

        assertEquals("New", existing.getFirstname());
        assertEquals("new@example.com", existing.getEmail());
        assertEquals(address, existing.getAddress());
        verify(repository).save(existing);
    }

    @Test
    void updateCustomer_shouldThrowIfNotFound() {
        CustomerRequest update = new CustomerRequest("New", "Doe", "new@example.com", null);
        when(repository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(CustomerNotFoundException.class,
                () -> service.updateCustomer(99, update));

        assertTrue(exception.getMessage().contains("No customer found"));
    }

    @Test
    void findAllCustomers_shouldReturnMappedList() {
        Customer customer = Customer.builder().id(1).firstname("Likhitha").email("li@example.com").build();
        CustomerResponse response = new CustomerResponse(1, "Likhitha", null, "li@example.com", null);

        when(repository.findAll()).thenReturn(List.of(customer));
        when(mapper.fromCustomer(customer)).thenReturn(response);

        List<CustomerResponse> result = service.findAllCustomers();

        assertEquals(1, result.size());
        assertEquals("li@example.com", result.get(0).email());
    }

    @Test
    void findById_shouldReturnCustomerResponse() {
        Customer customer = Customer.builder().id(2).firstname("Test").email("test@example.com").build();
        CustomerResponse response = new CustomerResponse(2, "Test", null, "test@example.com", null);

        when(repository.findById(2)).thenReturn(Optional.of(customer));
        when(mapper.fromCustomer(customer)).thenReturn(response);

        CustomerResponse result = service.findById(2);

        assertEquals("Test", result.firstname());
    }

    @Test
    void findById_shouldThrowIfNotFound() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> service.findById(999));
    }

    @Test
    void existsById_shouldReturnTrueIfPresent() {
        when(repository.findById(1)).thenReturn(Optional.of(new Customer()));
        assertTrue(service.existsById(1));
    }

    @Test
    void existsById_shouldReturnFalseIfAbsent() {
        when(repository.findById(2)).thenReturn(Optional.empty());
        assertFalse(service.existsById(2));
    }

    @Test
    void deleteCustomer_shouldCallRepository() {
        doNothing().when(repository).deleteById(5);
        service.deleteCustomer(5);
        verify(repository).deleteById(5);
    }
}
