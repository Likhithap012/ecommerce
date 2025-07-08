package com.brodgelabz.user_service.service;

import com.brodgelabz.user_service.dto.CustomerMapper;
import com.brodgelabz.user_service.dto.CustomerRequest;
import com.brodgelabz.user_service.dto.CustomerResponse;
import com.brodgelabz.user_service.entity.Customer;
import com.brodgelabz.user_service.exception.CustomerNotFoundException;
import com.brodgelabz.user_service.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public Integer createCustomer(CustomerRequest request) {
        var customer = this.repository.save(mapper.toCustomer(request));
        return customer.getId();
    }
    @Transactional
    public void updateCustomer(Integer customerId, CustomerRequest request) {
        var customer = this.repository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cannot update customer:: No customer found with the provided ID: %s", customerId)
                ));
        mergeCustomer(customer, request);
        this.repository.save(customer);
    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstname())) {
            customer.setFirstname(request.firstname());
        }
        if (StringUtils.isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }
        if (request.address() != null) {
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return  this.repository.findAll()
                .stream()
                .map(this.mapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public CustomerResponse findById(Integer id) {
        return this.repository.findById(id)
                .map(mapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("No customer found with the provided ID: %s", id)));
    }

    public boolean existsById(Integer id) {
        return this.repository.findById(id)
                .isPresent();
    }

    public void deleteCustomer(Integer id) {
        this.repository.deleteById(id);
    }
}
