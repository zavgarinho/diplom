package com.zavga.diplom.repository.customer;

import com.zavga.diplom.entity.customer.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @EntityGraph(attributePaths = {"objects"})
    List<Customer> findAll();

    @EntityGraph(attributePaths = {"objects"})
    Optional<Customer> findById(Long id);


}
