package com.zavga.diplom.dto.object;

import com.zavga.diplom.dto.customer.CustomerMapper;
import com.zavga.diplom.dto.equipment.EquipmentShortDTO;
import com.zavga.diplom.dto.work.InstallationWorkShortDTO;
import com.zavga.diplom.entity.equipment.Equipment;
import com.zavga.diplom.entity.object.SecurityObject;
import com.zavga.diplom.entity.work.InstallationWork;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CustomerMapper.class)
public interface SecurityObjectMapper {
    SecurityObjectShortDTO toShortDto(SecurityObject securityObject);

    @Mapping(target = "customer",ignore = true)
    @Mapping(target = "equipment",ignore = true)
    @Mapping(target = "works",ignore = true)
    SecurityObject toEntity(SecurityObjectRequestDTO securityObjectRequestDTO);

    SecurityObjectResponseDTO toResponseDto(SecurityObject securityObject);
    EquipmentShortDTO toEquipmentShortDTO(Equipment equipment);
    InstallationWorkShortDTO toInstallationWorkShortDTO(InstallationWork work);
}
