package com.amigoscode.customer.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amigoscode.customer.domain.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    
}
