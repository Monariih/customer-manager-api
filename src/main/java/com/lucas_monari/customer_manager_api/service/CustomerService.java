package com.lucas_monari.customer_manager_api.service;

import com.lucas_monari.customer_manager_api.domain.Customer;
import com.lucas_monari.customer_manager_api.domain.CustomerStatus;
import com.lucas_monari.customer_manager_api.dto.CustomerRequest;
import com.lucas_monari.customer_manager_api.dto.CustomerResponse;
import com.lucas_monari.customer_manager_api.dto.ScoreResponse;
import com.lucas_monari.customer_manager_api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ScoreIntegrationService scoreIntegrationService;

    @Transactional
    public CustomerResponse create(CustomerRequest request) {
        if (customerRepository.existsByCpf(request.cpf())) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }

        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setCpf(request.cpf());
        customer.setEmail(request.email());
        customer.setStatus(request.status());

        customer = customerRepository.save(customer);
        return new CustomerResponse(customer);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(Long id) {
        Customer customer = getCustomerById(id);
        return new CustomerResponse(customer);
    }

    @Transactional
    public CustomerResponse update(Long id, CustomerRequest request) {
        Customer customer = getCustomerById(id);

        if (!customer.getCpf().equals(request.cpf()) && customerRepository.existsByCpf(request.cpf())) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }

        customer.setName(request.name());
        customer.setCpf(request.cpf());
        customer.setEmail(request.email());
        customer.setStatus(request.status());

        customer = customerRepository.save(customer);
        return new CustomerResponse(customer);
    }

    @Transactional
    public void delete(Long id) {
        Customer customer = getCustomerById(id);
        customerRepository.delete(customer);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> findAll() {
        return customerRepository.findAll().stream()
                .map(CustomerResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> searchByName(String name) {
        return customerRepository.searchByNameNative(name).stream()
                .map(CustomerResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> findByStatus(CustomerStatus status) {
        return customerRepository.findByStatus(status).stream()
                .map(CustomerResponse::new)
                .toList();
    }

    public ScoreResponse getCustomerScore(Long id) {
        Customer customer = getCustomerById(id);
        return scoreIntegrationService.getCustomerScore(customer.getCpf());
    }

    private Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente inexistente."));
    }
}