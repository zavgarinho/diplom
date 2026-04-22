package com.zavga.diplom.service.object;


import com.zavga.diplom.dto.object.SecurityObjectMapper;
import com.zavga.diplom.dto.object.SecurityObjectRequestDTO;
import com.zavga.diplom.dto.object.SecurityObjectResponseDTO;
import com.zavga.diplom.entity.customer.Customer;
import com.zavga.diplom.entity.object.SecurityObject;
import com.zavga.diplom.repository.customer.CustomerRepository;
import com.zavga.diplom.repository.object.SecurityObjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SecurityObjectService {

    private final SecurityObjectRepository securityObjectRepository;
    private final SecurityObjectMapper mapper;
    private final CustomerRepository customerRepository;

    public SecurityObjectService(SecurityObjectRepository securityObjectRepository, SecurityObjectMapper mapper,
                                 CustomerRepository customerRepository){
        this.securityObjectRepository = securityObjectRepository;
        this.mapper = mapper;
        this.customerRepository = customerRepository;
    }

    public List<SecurityObjectResponseDTO> getAll(){
        var objects = this.securityObjectRepository.findAll();
        return objects.stream().map(o -> mapper.toResponseDto(o)).collect(Collectors.toList());
    }

    public Optional<SecurityObjectResponseDTO> getById(Long id){
        var object = this.securityObjectRepository.findById(id);
        if(object.isEmpty()){
            return Optional.empty();
        }
        SecurityObjectResponseDTO dto = mapper.toResponseDto(object.get());
        return Optional.of(dto);
    }
    @Transactional
    public SecurityObjectResponseDTO saveObject(SecurityObjectRequestDTO objectRequestDTO){
        var object = mapper.toEntity(objectRequestDTO);
        var customer = customerRepository.findById(objectRequestDTO.customerId()).orElseThrow();
        object.setCustomer(customer);
        var savedObject = securityObjectRepository.save(object);
        return mapper.toResponseDto(savedObject);
    }

    public SecurityObjectResponseDTO updateObject(Long id, SecurityObjectRequestDTO requestDTO){
        var customer = customerRepository.findById(requestDTO.customerId());

        if(securityObjectRepository.findById(id).isEmpty() || customer.isEmpty()){
            throw new NoSuchElementException();
        }
        var objectToUpdate = mapper.toEntity(requestDTO);
        objectToUpdate.setId(id);
        objectToUpdate.setCustomer(customer.get());
        var updatedObject = securityObjectRepository.save(objectToUpdate);
        return mapper.toResponseDto(updatedObject);
    }

    public void deleteObject(Long id){
        this.securityObjectRepository.deleteById(id);
    }
}
