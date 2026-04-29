package com.zavga.diplom.service.equipment;

import com.zavga.diplom.dto.equipment.EquipmentMapper;
import com.zavga.diplom.dto.equipment.EquipmentRequestDTO;
import com.zavga.diplom.dto.equipment.EquipmentResponseDTO;
import com.zavga.diplom.entity.equipment.Equipment;
import com.zavga.diplom.entity.equipment.EquipmentType;
import com.zavga.diplom.entity.object.SecurityObject;
import com.zavga.diplom.repository.equipment.EquipmentRepository;
import com.zavga.diplom.repository.object.SecurityObjectRepository;
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
class EquipmentServiceTest {

    @Mock
    EquipmentRepository equipmentRepository;

    @Mock
    EquipmentMapper equipmentMapper;

    @Mock
    SecurityObjectRepository objectRepository;

    @InjectMocks
    EquipmentService equipmentService;


    @Test
    void testFindAll_ReturnsList(){
        Equipment equipment1 = new Equipment();
        equipment1.setId(1L);
        Equipment equipment2 = new Equipment();
        equipment2.setId(2L);
        EquipmentResponseDTO responseDTO1 = createResponseDTO(1L);
        EquipmentResponseDTO responseDTO2 = createResponseDTO(2L);
        List<EquipmentResponseDTO> expected = List.of(responseDTO1,responseDTO2);

        when(equipmentRepository.findAll()).thenReturn(List.of(equipment1,equipment2));
        when(equipmentMapper.toResponseDTO(equipment1)).thenReturn(responseDTO1);
        when(equipmentMapper.toResponseDTO(equipment2)).thenReturn(responseDTO2);

        List<EquipmentResponseDTO> provided = equipmentService.findAll();

        assertEquals(expected,provided);
    }
    @Test
    void testFindAll_ReturnsEmptyList(){
        List<EquipmentResponseDTO> expected = List.of();
        when(equipmentRepository.findAll()).thenReturn(List.of());
        List<EquipmentResponseDTO> provided = equipmentService.findAll();
        assertEquals(expected,provided);
    }

    @Test
    void testFindById_ReturnsOptionalEquipmentResponseDTO(){
        Equipment equipment = new Equipment();
        equipment.setId(1L);
        EquipmentResponseDTO responseDTO = createResponseDTO(1L);
        Optional<EquipmentResponseDTO> expected = Optional.of(responseDTO);
        when(equipmentRepository.findById(1L)).thenReturn(Optional.of(equipment));
        when(equipmentMapper.toResponseDTO(equipment)).thenReturn(responseDTO);

        Optional<EquipmentResponseDTO> provided = equipmentService.findById(1L);

        assertEquals(expected,provided);
    }

    @Test
    void testFindById_ReturnsEmptyOptional(){
        Optional<EquipmentResponseDTO> expected = Optional.empty();
        when(equipmentRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<EquipmentResponseDTO> provided = equipmentService.findById(1L);

        assertEquals(expected,provided);
    }

    @Test
    void testSaveWithoutObject_ReturnsSavedEquipmentResponseDTO(){
        Equipment equipment = new Equipment();
        equipment.setId(1L);
        EquipmentRequestDTO requestDTO = createRequestDTO();
        EquipmentResponseDTO expected = createResponseDTO(1L);

        when(equipmentMapper.toEntity(requestDTO)).thenReturn(equipment);
        when(equipmentRepository.save(equipment)).thenReturn(equipment);
        when(equipmentMapper.toResponseDTO(equipment)).thenReturn(expected);

        EquipmentResponseDTO provided = equipmentService.save(requestDTO);

        assertEquals(expected,provided);
    }

    @Test
    void testSaveWithObject_ReturnsSavedEquipmentResponseDTO(){
        Equipment equipment = new Equipment();
        equipment.setId(1L);
        SecurityObject object = new SecurityObject();
        object.setId(10L);
        EquipmentRequestDTO requestDTO = new EquipmentRequestDTO("camera", "ajax", EquipmentType.VIDEO_SURVEILLANCE, 10L);
        EquipmentResponseDTO expected = createResponseDTO(1L);

        when(equipmentMapper.toEntity(requestDTO)).thenReturn(equipment);
        when(objectRepository.findById(10L)).thenReturn(Optional.of(object));
        when(equipmentRepository.save(equipment)).thenReturn(equipment);
        when(equipmentMapper.toResponseDTO(equipment)).thenReturn(expected);

        EquipmentResponseDTO provided = equipmentService.save(requestDTO);

        assertEquals(expected, provided);
    }

    @Test
    void testUpdate_ReturnsUpdatedResponseDTO(){
        Equipment equipment = new Equipment();
        EquipmentRequestDTO requestDTO = createRequestDTO();
        EquipmentResponseDTO expected = createResponseDTO(1L);

        when(equipmentRepository.findById(1L)).thenReturn(Optional.of(equipment));
        when(equipmentRepository.save(equipment)).thenReturn(equipment);
        when(equipmentMapper.toEntity(requestDTO)).thenReturn(equipment);
        when(equipmentMapper.toResponseDTO(equipment)).thenReturn(expected);

        EquipmentResponseDTO provided = equipmentService.update(1L, requestDTO);

        assertEquals(expected,provided);
    }

    @Test
    void testUpdate_ThrowsWhenNotFoundById(){

        when(equipmentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,()->{
            equipmentService.update(1L,createRequestDTO());
        });
    }

    @Test
    void testDelete_ReturnsTrueWhenDeleted(){

        when(equipmentRepository.deleteByIdWithCount(1L)).thenReturn(1);

        assertTrue(equipmentService.delete(1L));

    }

    @Test
    void testDelete_ReturnsFalseWhenNotDeleted(){

        when(equipmentRepository.deleteByIdWithCount(1L)).thenReturn(0);

        assertFalse(equipmentService.delete(1L));

    }




    EquipmentResponseDTO createResponseDTO(Long id){
        return new EquipmentResponseDTO(id,"camera","ajax", EquipmentType.VIDEO_SURVEILLANCE,null);
    }

    EquipmentRequestDTO createRequestDTO(){
        return new EquipmentRequestDTO("camera","ajax", EquipmentType.VIDEO_SURVEILLANCE,null);
    }
}