package com.rafi.customer_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rafi.customer_service.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
