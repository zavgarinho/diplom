package com.zavga.diplom.service.worker;

import com.zavga.diplom.dto.work.InstallationWorkShortDTO;
import com.zavga.diplom.dto.worker.WorkerMapper;
import com.zavga.diplom.dto.worker.WorkerRequestDTO;
import com.zavga.diplom.dto.worker.WorkerResponseDTO;
import com.zavga.diplom.entity.work.InstallationWork;
import com.zavga.diplom.entity.work.InstallationWorkStatus;
import com.zavga.diplom.entity.worker.Worker;
import com.zavga.diplom.entity.worker.WorkerSpecialization;
import com.zavga.diplom.repository.work.InstallationWorkRepository;
import com.zavga.diplom.repository.worker.WorkerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class WorkerServiceTest {

    @Mock
    WorkerRepository workerRepository;

    @Mock
    WorkerMapper workerMapper;

    @Mock
    InstallationWorkRepository workRepository;

    @InjectMocks
    WorkerService workerService;

    @Test
    void testGetAll_ReturnsListResponseDTO(){
        Worker worker1 = new Worker();
        worker1.setId(1L);
        Worker worker2 = new Worker();
        worker2.setId(2L);
        WorkerResponseDTO responseDTO1 = createResponseDTO(1L, null);
        WorkerResponseDTO responseDTO2 =  createResponseDTO(2L,null);

        List<WorkerResponseDTO> expected = List.of(responseDTO1,responseDTO2);

        when(workerRepository.findAll()).thenReturn(List.of(worker1,worker2));
        when(workerMapper.toResponseDTO(worker1)).thenReturn(responseDTO1);
        when(workerMapper.toResponseDTO(worker2)).thenReturn(responseDTO2);

        List<WorkerResponseDTO> provided = workerService.findAll();

        assertEquals(expected,provided);
    }

    @Test
    void testFindById_ReturnsResponseDTO(){
        Worker worker = new Worker();
        worker.setId(1L);
        WorkerResponseDTO expected = createResponseDTO(worker.getId(), null);

        when(workerRepository.findById(worker.getId())).thenReturn(Optional.of(worker));
        when(workerMapper.toResponseDTO(worker)).thenReturn(expected);

        WorkerResponseDTO provided = workerService.findById(1L);

        assertEquals(expected,provided);
    }

    @Test
    void testFindById_ReturnsNullWhenNotFound(){
        WorkerResponseDTO expected = null;

        when(workerRepository.findById(1L)).thenReturn(Optional.empty());

        WorkerResponseDTO provided = workerService.findById(1L);

        assertEquals(expected,provided);
    }

    @Test
    void testCreate_ReturnsResponseDTO(){
        InstallationWork work = new InstallationWork();
        work.setId(1L);
        WorkerRequestDTO requestDTO = createRequestDTO(List.of(work.getId()));
        Worker worker = new Worker();
        worker.setName(requestDTO.name());
        worker.setSpecialization(requestDTO.specialization());
        InstallationWorkShortDTO workShortDTO = createWorkShortDTO(1L);
        WorkerResponseDTO expected = createResponseDTO(1L,List.of(workShortDTO));

        when(workerMapper.toEntity(requestDTO)).thenReturn(worker);
        when(workerRepository.save(worker)).thenReturn(worker);
        when(workRepository.findAllById(List.of(work.getId()))).thenReturn(List.of(work));
        when(workerMapper.toResponseDTO(worker)).thenReturn(expected);

        WorkerResponseDTO provided = workerService.create(requestDTO);

        assertEquals(provided,expected);

    }

    @Test
    void testUpdate_ReturnsResponseDTO(){
        Worker existingWorker = new Worker();
        existingWorker.setId(1L);
        Worker mappedWorker = new Worker();
        InstallationWork work = new InstallationWork();
        work.setId(1L);
        InstallationWorkShortDTO workShortDTO = createWorkShortDTO(1L);
        WorkerRequestDTO requestDTO = createRequestDTO(List.of(work.getId()));
        WorkerResponseDTO expected = createResponseDTO(existingWorker.getId(), List.of(workShortDTO));
        mappedWorker.setName(requestDTO.name());
        mappedWorker.setSpecialization(requestDTO.specialization());

        when(workerRepository.findById(1L)).thenReturn(Optional.of(existingWorker));
        when(workerMapper.toEntity(requestDTO)).thenReturn(mappedWorker);
        when(workerRepository.save(mappedWorker)).thenReturn(mappedWorker);
        when(workRepository.findAllById(List.of(work.getId()))).thenReturn(List.of(work));
        when(workerMapper.toResponseDTO(mappedWorker)).thenReturn(expected);

        WorkerResponseDTO provided = workerService.update(1L,requestDTO);

        assertEquals(expected,provided);

    }

    @Test
    void testUpdate_ThrowsWhenWorkerNotFound(){
        WorkerRequestDTO requestDTO = createRequestDTO(null);
        when(workerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, ()->{
            workerService.update(1L, requestDTO);
        });
    }

    @Test
    void testDelete_ReturnsTrue(){
        Worker worker = new Worker();
        worker.setId(1L);
        when(workerRepository.findById(worker.getId())).thenReturn(Optional.of(worker));
        assertTrue(workerService.deleteById(worker.getId()));
    }

    @Test
    void testDelete_ReturnsFalse(){
        Worker worker = new Worker();
        worker.setId(1L);
        when(workerRepository.findById(worker.getId())).thenReturn(Optional.empty());
        assertFalse(workerService.deleteById(worker.getId()));
    }

    WorkerResponseDTO createResponseDTO(Long id, List<InstallationWorkShortDTO> works){
        return new WorkerResponseDTO(id,"name", WorkerSpecialization.ELECTRICIAN,works);
    }

    WorkerRequestDTO createRequestDTO(List<Long> worksId){
        return new WorkerRequestDTO("name", WorkerSpecialization.ELECTRICIAN,worksId);
    }

    InstallationWorkShortDTO createWorkShortDTO(Long id){
        return new InstallationWorkShortDTO(id,"name","description", InstallationWorkStatus.PLANNED, LocalDateTime.now(),LocalDateTime.now(),LocalDateTime.now());
    }

}