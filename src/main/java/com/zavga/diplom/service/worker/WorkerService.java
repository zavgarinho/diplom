package com.zavga.diplom.service.worker;

import com.zavga.diplom.dto.worker.WorkerMapper;
import com.zavga.diplom.dto.worker.WorkerRequestDTO;
import com.zavga.diplom.dto.worker.WorkerResponseDTO;
import com.zavga.diplom.entity.work.InstallationWork;
import com.zavga.diplom.repository.work.InstallationWorkRepository;
import com.zavga.diplom.repository.worker.WorkerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final WorkerMapper mapper;
    private final InstallationWorkRepository workRepository;
    public WorkerService(WorkerRepository workerRepository,
                         WorkerMapper mapper,
                         InstallationWorkRepository workRepository){
        this.workerRepository = workerRepository;
        this.mapper = mapper;
        this.workRepository = workRepository;
    }

    public List<WorkerResponseDTO> findAll(){
        var workers = workerRepository.findAll();
        return workers.stream().map(mapper::toResponseDTO).toList();
    }

    public WorkerResponseDTO findById(Long id){
        var worker = workerRepository.findById(id);
        return worker.map(mapper::toResponseDTO).orElse(null);
    }

    @Transactional
    public WorkerResponseDTO create(WorkerRequestDTO requestDTO){
        var workerToSave = mapper.toEntity(requestDTO);
        var savedWorker = workerRepository.save(workerToSave);
        if (requestDTO.worksId() != null && !requestDTO.worksId().isEmpty()) {
            var works = new HashSet<>(workRepository.findAllById(requestDTO.worksId()));
            for (var work : works) {
                work.getWorkers().add(savedWorker);
            }
            workRepository.saveAll(works);
            savedWorker.setWorks(works);
        }
        return mapper.toResponseDTO(savedWorker);
    }

    @Transactional
    public WorkerResponseDTO update(Long id, WorkerRequestDTO requestDTO){
        var existingWorker = workerRepository.findById(id).orElseThrow();
        for (var oldWork : existingWorker.getWorks()) {
            oldWork.getWorkers().remove(existingWorker);
        }
        workRepository.saveAll(existingWorker.getWorks());

        var worker = mapper.toEntity(requestDTO);
        worker.setId(id);
        var savedWorker = workerRepository.save(worker);
        if (requestDTO.worksId() != null && !requestDTO.worksId().isEmpty()) {
            var works = new HashSet<>(workRepository.findAllById(requestDTO.worksId()));
            for (var work : works) {
                work.getWorkers().add(savedWorker);
            }
            workRepository.saveAll(works);
            savedWorker.setWorks(works);
        }
        return mapper.toResponseDTO(savedWorker);
    }

    @Transactional
    public boolean deleteById(Long id){
        var worker = workerRepository.findById(id);
        if (worker.isEmpty()) return false;
        for (var work : worker.get().getWorks()) {
            work.getWorkers().remove(worker.get());
        }
        workerRepository.delete(worker.get());
        return true;
    }

}
