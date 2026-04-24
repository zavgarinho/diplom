package com.zavga.diplom.dto.work;

import com.zavga.diplom.dto.worker.WorkerShortDTO;

import java.util.List;

public record InstallationWorkResponseDTO(
        Long id,
        String name,
        String description,
        List<WorkerShortDTO> workers,
        Long objectId
) {
}
