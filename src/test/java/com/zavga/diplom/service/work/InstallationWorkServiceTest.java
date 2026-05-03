package com.zavga.diplom.service.work;

import com.zavga.diplom.dto.work.InstallationWorkMapper;
import com.zavga.diplom.dto.work.InstallationWorkResponseDTO;
import com.zavga.diplom.entity.work.InstallationWork;
import com.zavga.diplom.entity.work.InstallationWorkStatus;
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
        InstallationWorkResponseDTO responseDTO1 = createResponseDTO(1L);
        InstallationWorkResponseDTO responseDTO2 = createResponseDTO(2L);
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
        InstallationWorkResponseDTO expected = createResponseDTO(1L);
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

    InstallationWorkResponseDTO createResponseDTO(Long id){
        return new InstallationWorkResponseDTO(id,"name","description", InstallationWorkStatus.PLANNED,null,null);
    }


}