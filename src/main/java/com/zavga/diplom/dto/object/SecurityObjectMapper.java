package com.zavga.diplom.dto.object;

import com.zavga.diplom.dto.customer.CustomerMapper;
import com.zavga.diplom.dto.equipment.EquipmentShortDTO;
import com.zavga.diplom.entity.equipment.Equipment;
import com.zavga.diplom.entity.object.SecurityObject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CustomerMapper.class)
public interface SecurityObjectMapper {
    SecurityObjectShortDTO toShortDto(SecurityObject securityObject);

    @Mapping(target = "customer",ignore = true)
    @Mapping(target = "equipmentList",ignore = true)
    SecurityObject toEntity(SecurityObjectRequestDTO securityObjectRequestDTO);

    SecurityObjectResponseDTO toResponseDto(SecurityObject securityObject);
    EquipmentShortDTO toEquipmentShortDTO(Equipment equipment);

}
