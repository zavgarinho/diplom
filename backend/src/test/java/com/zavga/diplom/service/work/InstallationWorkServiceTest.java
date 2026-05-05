package com.zavga.diplom.service.work;

import com.zavga.diplom.dto.object.SecurityObjectShortDTO;
import com.zavga.diplom.dto.work.InstallationWorkMapper;
import com.zavga.diplom.dto.work.InstallationWorkRequestDTO;
import com.zavga.diplom.dto.work.InstallationWorkResponseDTO;
import com.zavga.diplom.dto.worker.WorkerShortDTO;
import com.zavga.diplom.entity.object.ObjectStatus;
import com.zavga.diplom.entity.object.ObjectType;
import com.zavga.diplom.entity.object.SecurityObject;
import com.zavga.diplom.entity.work.InstallationWork;
import com.zavga.diplom.entity.work.InstallationWorkStatus;
import com.zavga.diplom.entity.worker.Worker;
import com.zavga.diplom.entity.worker.WorkerSpecialization;
import com.zavga.diplom.repository.object.SecurityObjectRepository;
import com.zavga.diplom.repository.work.InstallationWorkRepository;
import com.zavga.diplom.repository.worker.WorkerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class InstallationWorkServiceTest {
    @Mock
    InstallationWorkRepository workRepository;
    @Mock
    InstallationWorkMapper workMapper;

    @Mock
    SecurityObjectRepository objectRepository;

    @Mock
    WorkerRepository workerRepository;

    @InjectMocks
    InstallationWorkService workService;

    @Test
    void testFindAll_ReturnListResponseDTO(){
        InstallationWork work1 = new InstallationWork();
        work1.setId(1L);
        InstallationWork work2 = new InstallationWork();
        work2.setId(2L);
        InstallationWorkResponseDTO responseDTO1 = createResponseDTO(1L, null, null);
        InstallationWorkResponseDTO responseDTO2 = createResponseDTO(2L, null, null);
        List<InstallationWorkResponseDTO> expected = List.of(responseDTO1, responseDTO2);
        when(workRepository.findAll()).thenReturn(List.of(work1,work2));
        when(workMapper.toResponseDTO(work1)).thenReturn(responseDTO1);
        when(workMapper.toResponseDTO(work2)).thenReturn(responseDTO2);

        List<InstallationWorkResponseDTO> provided = workService.findAll();

        assertEquals(expected,provided);
    }

    @Test
    void testFindById_ReturnResponseDTO(){
        InstallationWork work = new InstallationWork();
        work.setId(1L);
        InstallationWorkResponseDTO expected = createResponseDTO(1L, null, null);
        when(workRepository.findById(1L)).thenReturn(Optional.of(work));
        when(workMapper.toResponseDTO(work)).thenReturn(expected);

        InstallationWorkResponseDTO provided = workService.findById(1L);

        assertEquals(expected,provided);
    }

    @Test
    void testFindById_ThrowsWhenNotFound(){

        when(workRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, ()->{
            workService.findById(1L);
        });
    }

    @Test
    void testCreateWithWorkersAndObject_ReturnsResponseDTO(){
        InstallationWork work = new InstallationWork();
        work.setId(1L);
        SecurityObject object = new SecurityObject();
        object.setId(1L);
        Worker worker1 = new Worker();
        worker1.setId(1L);
        Worker worker2 = new Worker();
        worker2.setId(2L);
        List<WorkerShortDTO> workerShortDTOList = List.of(
                new WorkerShortDTO(worker1.getId(), "name 1", WorkerSpecialization.ELECTRICIAN),
                new WorkerShortDTO(worker2.getId(), "name 2", WorkerSpecialization.ELECTRICIAN)

        );
        SecurityObjectShortDTO objectShortDTO = createSecurityObjectShortDTO(object.getId());

        InstallationWorkRequestDTO requestDTO = createRequestDTO(List.of(worker1.getId(),worker2.getId()), object.getId());
        InstallationWorkResponseDTO expected = createResponseDTO(
                work.getId(),
                workerShortDTOList,
                objectShortDTO
        );

        when(workMapper.toEntity(requestDTO)).thenReturn(work);
        when(objectRepository.findById(object.getId())).thenReturn(Optional.of(object));
        when(workerRepository.findAllById(List.of(worker1.getId(),worker2.getId()))).thenReturn(List.of(worker1,worker2));
        when(workRepository.save(work)).thenReturn(work);
        when(workMapper.toResponseDTO(work)).thenReturn(expected);

        InstallationWorkResponseDTO provided = workService.create(requestDTO);

        assertEquals(expected,provided);
    }

    @Test
    void testCreate_ThrowsWhenObjectNotFound(){
        InstallationWork work = new InstallationWork();
        work.setId(1L);
        InstallationWorkRequestDTO requestDTO = createRequestDTO(null,1L);

        when(workMapper.toEntity(requestDTO)).thenReturn(work);
        when(objectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, ()->{
            workService.create(requestDTO);
        });
    }

    @Test
    void testUpdate_ReturnsResponseDTO(){
        InstallationWork currentWork = new InstallationWork();
        currentWork.setId(1L);
        SecurityObject object = new SecurityObject();
        object.setId(1L);
        currentWork.setObject(object);

        InstallationWork mappedWork = new InstallationWork();

        Worker worker1 = new Worker();
        worker1.setId(1L);
        Worker worker2 = new Worker();
        worker2.setId(2L);

        InstallationWorkRequestDTO requestDTO = createRequestDTO(List.of(worker1.getId(),worker2.getId()),object.getId());
        WorkerShortDTO worker1ShortDTO = createWorkerShortDTO(worker1.getId());
        WorkerShortDTO worker2ShortDTO = createWorkerShortDTO(worker2.getId());
        SecurityObjectShortDTO objectShortDTO = createSecurityObjectShortDTO(object.getId());
        InstallationWorkResponseDTO expected = createResponseDTO(currentWork.getId(),
                List.of(worker1ShortDTO,worker2ShortDTO),
                objectShortDTO
        );
        when(workRepository.findById(currentWork.getId())).thenReturn(Optional.of(currentWork));
        when(workMapper.toEntity(requestDTO)).thenReturn(mappedWork);
        when(workerRepository.findAllById(List.of(worker1.getId(),worker2.getId()))).thenReturn(List.of(worker1,worker2));
        when(workRepository.save(mappedWork)).thenReturn(mappedWork);
        when(workMapper.toResponseDTO(mappedWork)).thenReturn(expected);

        InstallationWorkResponseDTO provided = workService.update(currentWork.getId(), requestDTO);

        assertEquals(expected,provided);
    }

    @Test
    void testUpdate_ThrowsWhenWorkNotFound(){
        InstallationWorkRequestDTO requestDTO = createRequestDTO(null, null);
        when(workRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, ()->{
            workService.update(1L, requestDTO);
        });
    }

    @Test
    void testUpdate_ThrowsWhenObjectNotFound(){
        InstallationWork work = new InstallationWork();
        work.setId(1L);
        InstallationWorkRequestDTO requestDTO = createRequestDTO(null,1L);

        when(workRepository.findById(1L)).thenReturn(Optional.of(work));
        when(workMapper.toEntity(requestDTO)).thenReturn(work);
        when(objectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, ()->{
            workService.update(1L,requestDTO);
        });
    }

    @Test
    void testDelete_ReturnsTrue(){
        InstallationWork work = new InstallationWork();
        work.setId(1L);
        when(workRepository.findById(work.getId())).thenReturn(Optional.of(work));

        assertTrue(workService.deleteById(work.getId()));

    }

    @Test
    void testDelete_ReturnsFalse(){
        InstallationWork work = new InstallationWork();
        work.setId(1L);
        when(workRepository.findById(work.getId())).thenReturn(Optional.empty());

        assertFalse(workService.deleteById(work.getId()));

    }

    InstallationWorkResponseDTO createResponseDTO(Long id, List<WorkerShortDTO> workers, SecurityObjectShortDTO object){
        return new InstallationWorkResponseDTO(id,"name","description", InstallationWorkStatus.PLANNED,workers, object);
    }

    InstallationWorkRequestDTO createRequestDTO( List<Long> workersId, Long objectId){
        return new InstallationWorkRequestDTO("name","description", InstallationWorkStatus.PLANNED,workersId, objectId);
    }

    WorkerShortDTO createWorkerShortDTO(Long id){
        return new WorkerShortDTO(id,"name 1", WorkerSpecialization.ELECTRICIAN);
    }

    SecurityObjectShortDTO createSecurityObjectShortDTO(Long id){
        return new SecurityObjectShortDTO(
                id, "address",45.0,2, ObjectType.APARTMENT, ObjectStatus.NEW,null
        );
    }

}