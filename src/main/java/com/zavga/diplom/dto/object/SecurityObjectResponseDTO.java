package com.zavga.diplom.dto.object;

import com.zavga.diplom.dto.customer.CustomerShortDTO;
import com.zavga.diplom.entity.object.ObjectType;

public record SecurityObjectResponseDTO(
        Long id,
        String address,
        Double area,
        Integer floor,
        ObjectType type,
        CustomerShortDTO customer
) {
}
