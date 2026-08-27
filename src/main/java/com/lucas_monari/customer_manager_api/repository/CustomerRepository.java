package com.lucas_monari.customer_manager_api.repository;

import com.lucas_monari.customer_manager_api.domain.Customer;
import com.lucas_monari.customer_manager_api.domain.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerRepositoryCustom {

    boolean existsByCpf(String cpf);

    List<Customer> findByStatus(CustomerStatus status);
}