package com.zavga.diplom.dto.equipment;

import com.zavga.diplom.dto.object.SecurityObjectShortDTO;
import com.zavga.diplom.entity.equipment.EquipmentType;
import com.zavga.diplom.entity.object.ObjectType;

public record EquipmentResponseDTO(
        Long id,
        String name,
        String manufacturer,
        EquipmentType type,
        SecurityObjectShortDTO object
) {
}
