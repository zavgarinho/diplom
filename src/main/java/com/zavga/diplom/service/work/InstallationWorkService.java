package com.zavga.diplom.service.work;


import com.zavga.diplom.dto.work.InstallationWorkMapper;
import com.zavga.diplom.dto.work.InstallationWorkRequestDTO;
import com.zavga.diplom.dto.work.InstallationWorkResponseDTO;
import com.zavga.diplom.repository.object.SecurityObjectRepository;
import com.zavga.diplom.repository.work.InstallationWorkRepository;
import com.zavga.diplom.repository.worker.WorkerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class InstallationWorkService {
    private final InstallationWorkRepository workRepository;
    private final InstallationWorkMapper mapper;
    private final SecurityObjectRepository objectRepository;
    private final WorkerRepository workerRepository;

    public InstallationWorkService(InstallationWorkRepository workRepository,
                                   InstallationWorkMapper mapper,
                                   SecurityObjectRepository securityObjectRepository,
                                   WorkerRepository workerRepository){
        this.workRepository = workRepository;
        this.mapper = mapper;
        this.objectRepository = securityObjectRepository;
        this.workerRepository = workerRepository;
    }

    public List<InstallationWorkResponseDTO> findAll(){
        var works = workRepository.findAll();
        return works.stream().map(mapper::toResponseDTO).toList();
    }

    public InstallationWorkResponseDTO findById(Long id){
        var work = workRepository.findById(id).orElseThrow();
        return mapper.toResponseDTO(work);
    }
    @Transactional
    public InstallationWorkResponseDTO create(InstallationWorkRequestDTO requestDTO){
        var workToSave = mapper.toEntity(requestDTO);
        if (requestDTO.objectId() != null) {
            var object = objectRepository.findById(requestDTO.objectId()).orElseThrow();
            workToSave.setObject(object);
        }
        if (requestDTO.workersId() != null && !requestDTO.workersId().isEmpty()) {
            var workers = new HashSet<>(workerRepository.findAllById(requestDTO.workersId()));
            workToSave.setWorkers(workers);
        }
        var savedWork = workRepository.save(workToSave);
        return mapper.toResponseDTO(savedWork);
    }
    @Transactional
    public InstallationWorkResponseDTO update(Long id,InstallationWorkRequestDTO requestDTO){
        var current = workRepository.findById(id).orElseThrow();
        var workToUpdate = mapper.toEntity(requestDTO);
        workToUpdate.setId(id);
        if (requestDTO.objectId() != null && !requestDTO.objectId().equals(current.getObject().getId())) {
            var object = objectRepository.findById(requestDTO.objectId()).orElseThrow();
            workToUpdate.setObject(object);
        }else {
            workToUpdate.setObject(current.getObject());
        }
        if (requestDTO.workersId() != null && !requestDTO.workersId().isEmpty()) {
            var workers = new HashSet<>(workerRepository.findAllById(requestDTO.workersId()));
            workToUpdate.setWorkers(workers);
        }
        var updatedWork = workRepository.save(workToUpdate);
        return mapper.toResponseDTO(updatedWork);
    }
    @Transactional
    public boolean deleteById(Long id){
        var work = workRepository.findById(id);
        if (work.isEmpty()) return false;
        workRepository.delete(work.get());
        return true;
    }

}
