package com.zavga.diplom.repository.customer;

import com.zavga.diplom.entity.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
