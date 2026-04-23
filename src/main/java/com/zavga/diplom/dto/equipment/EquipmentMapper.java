package com.zavga.diplom.dto.equipment;

import com.zavga.diplom.dto.object.SecurityObjectMapper;
import com.zavga.diplom.entity.equipment.Equipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = SecurityObjectMapper.class)
public interface EquipmentMapper {
    EquipmentShortDTO toShortDTO(Equipment equipment);
    @Mapping(target = "object", ignore = true)
    Equipment toEntity(EquipmentRequestDTO requestDTO);
    EquipmentResponseDTO toResponseDTO(Equipment equipment);
}
