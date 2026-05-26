package com.zavga.diplom.dto.work;

import com.zavga.diplom.entity.work.InstallationWorkStatus;
import jakarta.persistence.Column;

import java.time.LocalDateTime;
import java.util.List;

public record InstallationWorkRequestDTO(
        String name,
        String description,
        InstallationWorkStatus status,
        LocalDateTime startTime,
        LocalDateTime plannedEndTime,
        LocalDateTime realEndTime,
        List<Long> workersId,
        Long objectId

) {
}
