package com.zavga.diplom.dto.object;

import com.zavga.diplom.entity.object.ObjectStatus;
import com.zavga.diplom.entity.object.ObjectType;

import java.util.List;

public record SecurityObjectRequestDTO (
    String address,
    Double area,
    Integer floor,
    ObjectType type,
    ObjectStatus status,
    Long customerId,
    List<Long> worksId
) {
}
