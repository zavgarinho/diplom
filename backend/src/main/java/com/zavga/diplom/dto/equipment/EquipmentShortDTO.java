package com.zavga.diplom.dto.equipment;

import com.zavga.diplom.entity.equipment.EquipmentType;

public record EquipmentShortDTO(
        Long id,
        String name,
        String manufacturer,
        EquipmentType type
) {
}
