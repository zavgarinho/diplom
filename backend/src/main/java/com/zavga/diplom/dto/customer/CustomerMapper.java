package com.zavga.diplom.dto.customer;

import com.zavga.diplom.dto.object.SecurityObjectShortDTO;
import com.zavga.diplom.entity.customer.Customer;
import com.zavga.diplom.entity.object.SecurityObject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toEntity(CustomerRequestDTO customerDTO);
    CustomerResponseDTO toResponseDto(Customer customer);
    CustomerShortDTO toShortDto(Customer customer);
    SecurityObjectShortDTO toSecurityObjectShortDto(SecurityObject securityObject);
}
