package com.zavga.diplom.dto.worker;

import com.zavga.diplom.dto.work.InstallationWorkShortDTO;
import com.zavga.diplom.entity.worker.WorkerSpecialization;

import java.util.List;

public record WorkerResponseDTO(
        Long id,
        String name,
        WorkerSpecialization specialization,
        List<InstallationWorkShortDTO> works
) {
}
