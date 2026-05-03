package com.zavga.diplom.service.customer;

import com.zavga.diplom.dto.customer.CustomerMapper;
import com.zavga.diplom.dto.customer.CustomerRequestDTO;
import com.zavga.diplom.dto.customer.CustomerResponseDTO;
import com.zavga.diplom.entity.customer.Customer;
import com.zavga.diplom.exception.EntityHasDependentsException;
import com.zavga.diplom.repository.customer.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper){
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public List<CustomerResponseDTO> getAll(){
        var customers = this.customerRepository.findAll();
        return customers.stream().map(c -> customerMapper.toResponseDto(c))
                .collect(Collectors.toList());
    }
    public Optional<CustomerResponseDTO> getById(Long id){
        var customer = this.customerRepository.findById(id);
        return customer.map(customerMapper::toResponseDto);

    }
    @Transactional
    public CustomerResponseDTO saveCustomer(CustomerRequestDTO customer){
        Customer customerToSave = customerMapper.toEntity(customer);
        var savedCustomer =  this.customerRepository.save(customerToSave);
        return customerMapper.toResponseDto(savedCustomer);
    }
    @Transactional
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO customer){
        Customer customerToSave = customerMapper.toEntity(customer);
        customerToSave.setId(id);
        var savedCustomer = this.customerRepository.save(customerToSave);
        return customerMapper.toResponseDto(savedCustomer);
    }
    @Transactional
    public boolean deleteCustomer(Long id){
        var customer = this.customerRepository.findById(id);
        if(customer.isEmpty()){
            return false;
        }
        if(!customer.get().getObjects().isEmpty()){
            throw new EntityHasDependentsException(
                    "Неможливо видалити замовника, доки за ним закріплені об'єкти охорони");
        }
        this.customerRepository.deleteById(id);
        return true;
    }
}
