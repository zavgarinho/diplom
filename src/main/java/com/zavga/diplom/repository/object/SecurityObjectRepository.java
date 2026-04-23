package com.zavga.diplom.repository.object;

import com.zavga.diplom.entity.customer.Customer;
import com.zavga.diplom.entity.object.SecurityObject;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SecurityObjectRepository extends JpaRepository<SecurityObject,Long> {
    @EntityGraph(attributePaths = {"customer"})
    List<SecurityObject> findAll();

    @EntityGraph(attributePaths = {"customer"})
    Optional<SecurityObject> findById(Long id);
}
