package com.zavga.diplom.dto.work;

import com.zavga.diplom.dto.object.SecurityObjectShortDTO;
import com.zavga.diplom.dto.worker.WorkerShortDTO;
import com.zavga.diplom.entity.work.InstallationWorkStatus;

import java.time.LocalDateTime;
import java.util.List;

public record InstallationWorkResponseDTO(
        Long id,
        String name,
        String description,
        InstallationWorkStatus status,
        LocalDateTime startTime,
        LocalDateTime plannedEndTime,
        LocalDateTime realEndTime,
        List<WorkerShortDTO> workers,
        List<Long> predecessorIds,
        SecurityObjectShortDTO object
) {
}
