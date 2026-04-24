package com.zavga.diplom.dto.worker;


import com.zavga.diplom.dto.work.InstallationWorkShortDTO;
import com.zavga.diplom.entity.work.InstallationWork;
import com.zavga.diplom.entity.worker.Worker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkerMapper {
    @Mapping(target = "works", ignore = true)
    Worker toEntity(WorkerRequestDTO requestDTO);
    WorkerShortDTO toShortDTO(Worker worker);
    WorkerResponseDTO toResponseDTO(Worker worker);
    InstallationWorkShortDTO toInstallationWorkShortDTO(InstallationWork work);
}
