package com.zavga.diplom.dto.work;


import com.zavga.diplom.entity.work.InstallationWorkStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record InstallationWorkShortDTO(
        Long id,
        String name,
        String description,
        InstallationWorkStatus status,
        LocalDateTime startTime,
        LocalDateTime plannedEndTime,
        LocalDateTime realEndTime
) {
}
