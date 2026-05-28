package com.zavga.diplom.dto.work;


import com.zavga.diplom.dto.object.SecurityObjectShortDTO;
import com.zavga.diplom.dto.worker.WorkerMapper;
import com.zavga.diplom.entity.object.SecurityObject;
import com.zavga.diplom.entity.work.InstallationWork;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = WorkerMapper.class)
public interface InstallationWorkMapper {

    @Mapping(target = "workers", ignore = true)
    @Mapping(target = "object", ignore = true)
    @Mapping(target = "predecessorWorks", ignore = true)
    InstallationWork toEntity(InstallationWorkRequestDTO requestDTO);

    SecurityObjectShortDTO toSecurityObjectShortDTO(SecurityObject object);

    @Mapping(target = "predecessorIds", source = "predecessorWorks", qualifiedByName = "worksToIds")
    InstallationWorkResponseDTO toResponseDTO(InstallationWork work);

    @Mapping(target = "predecessorIds", source = "predecessorWorks", qualifiedByName = "worksToIds")
    InstallationWorkShortDTO toShortDTO(InstallationWork work);

    @Named("worksToIds")
    default List<Long> worksToIds(Set<InstallationWork> works) {
        if (works == null) return List.of();
        return works.stream().map(InstallationWork::getId).toList();
    }
}
