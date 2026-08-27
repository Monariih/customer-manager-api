package com.lucas_monari.customer_manager_api.dto;

import com.lucas_monari.customer_manager_api.domain.Customer;
import com.lucas_monari.customer_manager_api.domain.CustomerStatus;

public record CustomerResponse(
        Long id,
        String name,
        String cpf,
        String email,
        CustomerStatus status
) {
    public CustomerResponse(Customer customer) {
        this(
                customer.getId(),
                customer.getName(),
                customer.getCpf(),
                customer.getEmail(),
                customer.getStatus()
        );
    }
}