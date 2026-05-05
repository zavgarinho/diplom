package com.zavga.diplom.dto.work;


import com.zavga.diplom.entity.work.InstallationWorkStatus;

public record InstallationWorkShortDTO(
        Long id,
        String name,
        String description,
        InstallationWorkStatus status
) {
}
