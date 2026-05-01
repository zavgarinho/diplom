package com.zavga.diplom.service.object;

import com.zavga.diplom.dto.customer.CustomerRequestDTO;
import com.zavga.diplom.dto.object.SecurityObjectMapper;
import com.zavga.diplom.dto.object.SecurityObjectRequestDTO;
import com.zavga.diplom.dto.object.SecurityObjectResponseDTO;
import com.zavga.diplom.entity.customer.Customer;
import com.zavga.diplom.entity.object.ObjectStatus;
import com.zavga.diplom.entity.object.ObjectType;
import com.zavga.diplom.entity.object.SecurityObject;
import com.zavga.diplom.entity.work.InstallationWork;
import com.zavga.diplom.repository.customer.CustomerRepository;
import com.zavga.diplom.repository.object.SecurityObjectRepository;
import com.zavga.diplom.repository.work.InstallationWorkRepository;
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
class SecurityObjectServiceTest {

    @Mock
    SecurityObjectRepository objectRepository;
    @Mock
    SecurityObjectMapper objectMapper;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    InstallationWorkRepository workRepository;

    @InjectMocks
    SecurityObjectService objectService;

    @Test
    void testGetAll_ReturnsListSecurityObjectResponseDTO(){
        SecurityObject object1 = new SecurityObject();
        object1.setId(1L);
        SecurityObject object2 = new SecurityObject();
        object2.setId(2L);
        SecurityObjectResponseDTO responseDTO1 = createResponseDTO(1L);
        SecurityObjectResponseDTO responseDTO2 = createResponseDTO(2L);
        List<SecurityObjectResponseDTO> expected = List.of(responseDTO1,responseDTO2);

        when(objectRepository.findAll()).thenReturn(List.of(object1,object2));
        when(objectMapper.toResponseDto(object1)).thenReturn(responseDTO1);
        when(objectMapper.toResponseDto(object2)).thenReturn(responseDTO2);

        List<SecurityObjectResponseDTO> provided = objectService.getAll();

        assertEquals(expected,provided);


    }

    @Test
    void testGetById_ReturnsSecurityObjectResponseDTO(){
        SecurityObject object = new SecurityObject();
        object.setId(1L);
        SecurityObjectResponseDTO responseDTO = createResponseDTO(1L);
        Optional<SecurityObjectResponseDTO> expected = Optional.of(responseDTO);
        when(objectRepository.findById(1L)).thenReturn(Optional.of(object));
        when(objectMapper.toResponseDto(object)).thenReturn(responseDTO);

        Optional<SecurityObjectResponseDTO> provided = objectService.getById(1L);

        assertEquals(expected,provided);
    }

    @Test
    void testGetById_ReturnsEmptyOptional(){
        Optional<SecurityObjectResponseDTO> expected = Optional.empty();
        when(objectRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<SecurityObjectResponseDTO> provided = objectService.getById(1L);

        assertEquals(expected,provided);
    }

    @Test
    void testSaveObject_ReturnsSavedSecurityObjectResponseDTO(){
        SecurityObject object = new SecurityObject();
        Customer customer = new Customer();
        customer.setId(1L);
        SecurityObjectResponseDTO responseDTO = createResponseDTO(1L);
        SecurityObjectRequestDTO requestDTO = createRequestDTO(1L,null,null);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(objectMapper.toEntity(requestDTO)).thenReturn(object);
        when(objectRepository.save(object)).thenReturn(object);
        when(objectMapper.toResponseDto(object)).thenReturn(responseDTO);

        SecurityObjectResponseDTO provided = objectService.saveObject(requestDTO);

        assertEquals(responseDTO,provided);
    }

    @Test
    void testSaveObject_ThrowsWhenCustomerNotFound(){
        SecurityObject object = new SecurityObject();
        SecurityObjectRequestDTO requestDTO = createRequestDTO(1L,null,null);
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        when(objectMapper.toEntity(requestDTO)).thenReturn(object);

        assertThrows(NoSuchElementException.class, ()->{
            objectService.saveObject(requestDTO);
        });
    }

    @Test
    void testUpdateObject_ReturnsUpdatedSecurityObjectResponseDTO(){
        SecurityObject object = new SecurityObject();
        Customer customer = new Customer();
        customer.setId(1L);
        InstallationWork work1 = new InstallationWork();
        work1.setId(1L);
        InstallationWork work2 = new InstallationWork();
        work2.setId(2L);
        SecurityObjectRequestDTO requestDTO = createRequestDTO(1L,null,List.of(1L,2L));
        SecurityObjectResponseDTO expected = createResponseDTO(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(workRepository.findAllById(List.of(1L,2L))).thenReturn(List.of(work1,work2));
        when(objectRepository.findById(1L)).thenReturn(Optional.of(object));
        when(objectRepository.save(object)).thenReturn(object);
        when(objectMapper.toEntity(requestDTO)).thenReturn(object);
        when(objectMapper.toResponseDto(object)).thenReturn(expected);

        SecurityObjectResponseDTO provided = objectService.updateObject(1L, requestDTO);

        assertEquals(expected,provided);
    }
    @Test
    void testUpdateObject_ThrowsWhenNotFound(){
        SecurityObjectRequestDTO requestDTO = createRequestDTO(1L,null,List.of(1L,2L));

        when(objectRepository.findById(1L)).thenReturn(Optional.empty());


        assertThrows(NoSuchElementException.class, ()->{
            objectService.updateObject(1L,requestDTO);
        });
    }

    SecurityObjectResponseDTO createResponseDTO(Long id){
        return new SecurityObjectResponseDTO(id,"Address", 40.0,4, ObjectType.APARTMENT, ObjectStatus.NEW,null,null,null);
    }
    SecurityObjectRequestDTO createRequestDTO(Long customerID, List<Long> equipmentIds,  List<Long> worksIds){
        return new SecurityObjectRequestDTO("Address", 40.0,4, ObjectType.APARTMENT, ObjectStatus.NEW,customerID,equipmentIds,worksIds);
    }



}