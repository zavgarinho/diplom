package com.zavga.diplom.dto.work;

import java.util.List;

public record InstallationWorkRequestDTO(
        String name,
        String description,
        List<Long> workersId,
        Long objectId
) {
}
