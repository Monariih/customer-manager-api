package com.lucas_monari.customer_manager_api.controller;

import com.lucas_monari.customer_manager_api.domain.CustomerStatus;
import com.lucas_monari.customer_manager_api.dto.CustomerRequest;
import com.lucas_monari.customer_manager_api.dto.CustomerResponse;
import com.lucas_monari.customer_manager_api.dto.ScoreResponse;
import com.lucas_monari.customer_manager_api.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestBody @Valid CustomerRequest request) {
        CustomerResponse response = customerService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable Long id, @RequestBody @Valid CustomerRequest request) {
        return ResponseEntity.ok(customerService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll(
            @RequestParam(required = false) CustomerStatus status) {
        if (status != null) {
            return ResponseEntity.ok(customerService.findByStatus(status));
        }
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponse>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(customerService.searchByName(name));
    }

    @GetMapping("/{id}/score")
    public ResponseEntity<ScoreResponse> getScore(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerScore(id));
    }
}