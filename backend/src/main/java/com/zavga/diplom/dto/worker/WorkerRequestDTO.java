package com.zavga.diplom.dto.worker;

import com.zavga.diplom.entity.worker.WorkerSpecialization;

import java.util.List;

public record WorkerRequestDTO(
        String name,
        WorkerSpecialization specialization,
        List<Long> worksId
) {
}
