package com.zavga.diplom.dto.worker;

import com.zavga.diplom.entity.worker.WorkerSpecialization;

public record WorkerShortDTO(
        Long id,
        String name,
        WorkerSpecialization specialization
) {
}
