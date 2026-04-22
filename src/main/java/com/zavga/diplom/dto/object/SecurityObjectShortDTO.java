package com.zavga.diplom.dto.object;

import com.zavga.diplom.entity.object.ObjectType;

public record SecurityObjectShortDTO(
        Long id,
        String address,
        Double area,
        Integer floor,
        ObjectType type
) {
}
