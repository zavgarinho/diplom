package com.zavga.diplom.dto.work;

import com.zavga.diplom.entity.work.InstallationWorkStatus;

import java.util.List;

public record InstallationWorkRequestDTO(
        String name,
        String description,
        InstallationWorkStatus status,
        List<Long> workersId,
        Long objectId
) {
}
