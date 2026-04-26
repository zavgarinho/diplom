package com.zavga.diplom.dto.object;

import com.zavga.diplom.dto.customer.CustomerShortDTO;
import com.zavga.diplom.dto.work.InstallationWorkShortDTO;
import com.zavga.diplom.entity.object.ObjectStatus;
import com.zavga.diplom.entity.object.ObjectType;

import java.util.List;

public record SecurityObjectResponseDTO(
        Long id,
        String address,
        Double area,
        Integer floor,
        ObjectType type,
        ObjectStatus status,
        CustomerShortDTO customer,
        List<InstallationWorkShortDTO> works
) {
}
