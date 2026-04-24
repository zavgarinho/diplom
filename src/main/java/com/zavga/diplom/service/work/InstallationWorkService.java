package com.zavga.diplom.service.work;


import com.zavga.diplom.dto.work.InstallationWorkMapper;
import com.zavga.diplom.dto.work.InstallationWorkRequestDTO;
import com.zavga.diplom.dto.work.InstallationWorkResponseDTO;
import com.zavga.diplom.repository.object.SecurityObjectRepository;
import com.zavga.diplom.repository.work.InstallationWorkRepository;
import com.zavga.diplom.repository.worker.WorkerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
        var object = objectRepository.findById(requestDTO.objectId()).orElseThrow();
        var workers = workerRepository.findAllById(requestDTO.workersId());
        workToSave.setObject(object);
        workToSave.setWorkers(workers);
        var savedWork = workRepository.save(workToSave);
        return mapper.toResponseDTO(savedWork);
    }
    @Transactional
    public InstallationWorkResponseDTO update(Long id,InstallationWorkRequestDTO requestDTO){
        if(!workRepository.existsById(id)){
            throw new NoSuchElementException();
        }
        var workToUpdate = mapper.toEntity(requestDTO);
        var object = objectRepository.findById(requestDTO.objectId()).orElseThrow();
        var workers = workerRepository.findAllById(requestDTO.workersId());
        workToUpdate.setId(id);
        workToUpdate.setObject(object);
        workToUpdate.setWorkers(workers);
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
