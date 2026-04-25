package com.zavga.diplom.dto.work;


import com.zavga.diplom.dto.worker.WorkerMapper;
import com.zavga.diplom.entity.work.InstallationWork;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = WorkerMapper.class)
public interface InstallationWorkMapper {

    @Mapping(target = "workers",ignore = true)
    @Mapping(target = "object",ignore = true)
    InstallationWork toEntity(InstallationWorkRequestDTO requestDTO);

    @Mapping(source = "object.id", target = "objectId")
    InstallationWorkResponseDTO toResponseDTO(InstallationWork work);
    InstallationWorkShortDTO toShortDTO(InstallationWork work);
}
