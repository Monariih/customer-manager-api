package com.lucas_monari.customer_manager_api.repository;

import com.lucas_monari.customer_manager_api.domain.Customer;
import com.lucas_monari.customer_manager_api.domain.CustomerStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Customer> searchByNameNative(String name) {
        // Native Query
        String sql = "SELECT * FROM tb_customers WHERE LOWER(name) LIKE LOWER(?)";
        String searchParam = "%" + name + "%";

        RowMapper<Customer> rowMapper = (rs, rowNum) -> {
            Customer customer = new Customer();
            customer.setId(rs.getLong("id"));
            customer.setName(rs.getString("name"));
            customer.setCpf(rs.getString("cpf"));
            customer.setEmail(rs.getString("email"));
            customer.setStatus(CustomerStatus.valueOf(rs.getString("status")));
            return customer;
        };

        return jdbcTemplate.query(sql, rowMapper, searchParam);
    }
}