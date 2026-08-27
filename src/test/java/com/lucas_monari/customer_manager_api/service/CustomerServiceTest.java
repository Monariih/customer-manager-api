package com.lucas_monari.customer_manager_api.service;

import com.lucas_monari.customer_manager_api.domain.Customer;
import com.lucas_monari.customer_manager_api.domain.CustomerStatus;
import com.lucas_monari.customer_manager_api.dto.CustomerRequest;
import com.lucas_monari.customer_manager_api.dto.CustomerResponse;
import com.lucas_monari.customer_manager_api.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ScoreIntegrationService scoreIntegrationService;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void create_ShouldSaveCustomer_WhenCpfIsUnique() {
        CustomerRequest request = new CustomerRequest("Lucas", "12345678901", "lucas@email.com", CustomerStatus.ACTIVE);
        Customer savedCustomer = new Customer(1L, "Lucas", "12345678901", "lucas@email.com", CustomerStatus.ACTIVE);

        when(customerRepository.existsByCpf(request.cpf())).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerResponse response = customerService.create(request);

        assertNotNull(response);
        assertEquals("Lucas", response.name());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void create_ShouldThrowException_WhenCpfAlreadyExists() {
        CustomerRequest request = new CustomerRequest("Lucas", "12345678901", "lucas@email.com", CustomerStatus.ACTIVE);

        when(customerRepository.existsByCpf(request.cpf())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> customerService.create(request));

        assertEquals("CPF já cadastrado.", exception.getMessage());
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void findById_ShouldThrowException_WhenCustomerDoesNotExist() {
        Long id = 99L;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> customerService.findById(id));

        assertEquals("Cliente inexistente.", exception.getMessage());
    }
}