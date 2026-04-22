package com.zavga.diplom.dto.object;

import com.zavga.diplom.dto.customer.CustomerMapper;
import com.zavga.diplom.entity.object.SecurityObject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CustomerMapper.class)
public interface SecurityObjectMapper {
    SecurityObjectShortDTO toShortDto(SecurityObject securityObject);

    @Mapping(target = "customer",ignore = true)
    SecurityObject toEntity(SecurityObjectRequestDTO securityObjectRequestDTO);

    SecurityObjectResponseDTO toResponseDto(SecurityObject securityObject);
}
