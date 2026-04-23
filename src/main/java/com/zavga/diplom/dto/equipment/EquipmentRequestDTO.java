package com.zavga.diplom.dto.equipment;

import com.zavga.diplom.entity.equipment.EquipmentType;

public record EquipmentRequestDTO(
        String name,
        String manufacturer,
        EquipmentType type,
        Long objectId
) {
}
