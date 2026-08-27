package com.lucas_monari.customer_manager_api.repository;

import com.lucas_monari.customer_manager_api.domain.Customer;
import java.util.List;

public interface CustomerRepositoryCustom {
    List<Customer> searchByNameNative(String name);
}