package com.zavga.diplom.service.customer;

import com.zavga.diplom.dto.customer.CustomerMapper;
import com.zavga.diplom.dto.customer.CustomerRequestDTO;
import com.zavga.diplom.dto.customer.CustomerResponseDTO;
import com.zavga.diplom.entity.customer.Customer;
import com.zavga.diplom.entity.customer.CustomerType;
import com.zavga.diplom.repository.customer.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    CustomerMapper mapper;

    @InjectMocks
    CustomerService customerService;
    @Test
    void testGetAll_ReturnsListCustomerResponseDTO(){
        Customer customer = new Customer();
        customer.setId(1L);
        Customer customer2 = new Customer();
        customer2.setId(2L);
        CustomerResponseDTO responseDTO = createResponseDto(1L);
        CustomerResponseDTO responseDTO2 = createResponseDto(2L);
        List<CustomerResponseDTO> customersExpected = List.of(responseDTO,responseDTO2);
        when(customerRepository.findAll()).thenReturn(List.of(customer,customer2));
        when(mapper.toResponseDto(customer)).thenReturn(responseDTO);
        when(mapper.toResponseDto(customer2)).thenReturn(responseDTO2);

        List<CustomerResponseDTO> customersProvided = customerService.getAll();

        assertEquals(customersExpected,customersProvided);

    }

    @Test
    void testGetById_ReturnsOptionalCustomerResponseDTO(){
        Customer customer = new Customer();
        customer.setId(1L);
        CustomerResponseDTO responseDTO = createResponseDto(1L);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(mapper.toResponseDto(customer)).thenReturn(responseDTO);
        Optional<CustomerResponseDTO> expected = Optional.of(responseDTO);

        Optional<CustomerResponseDTO> provided = customerService.getById(1L);

        assertEquals(expected,provided);

    }

    @Test
    void testGetById_ReturnsEmptyOptional(){

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<CustomerResponseDTO> provided = customerService.getById(1L);

        assertEquals(Optional.empty(),provided);
    }

    @Test
    void testSaveCustomer_ReturnsSavedCustomerResponseDTO(){
        CustomerRequestDTO requestDTO = createRequestDto();
        Customer customer = new Customer();
        customer.setId(1L);
        CustomerResponseDTO expectedResponseDTO = createResponseDto(1L);
        when(mapper.toEntity(requestDTO)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(mapper.toResponseDto(customer)).thenReturn(expectedResponseDTO);

        CustomerResponseDTO provided = customerService.saveCustomer(requestDTO);

        assertEquals(expectedResponseDTO,provided);

    }

    @Test
    void testUpdateCustomer_ReturnsUpdatedCustomer(){
        CustomerRequestDTO requestDTO = createRequestDto();
        Customer customer = new Customer();
        CustomerResponseDTO expectedResponseDTO = createResponseDto(1L);


        when(mapper.toEntity(requestDTO)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(mapper.toResponseDto(customer)).thenReturn(expectedResponseDTO);

        CustomerResponseDTO provided = customerService.updateCustomer(1L,requestDTO);

        assertEquals(expectedResponseDTO,provided);
    }

    @Test
    void testDeleteCustomer_ReturnsTrue(){
        Customer customer = new Customer();
        customer.setId(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        assertTrue(customerService.deleteCustomer(1L));
    }

    @Test
    void testDeleteCustomer_ReturnsFalse(){
        Customer customer = new Customer();
        customer.setId(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertFalse(customerService.deleteCustomer(1L));
    }

    private CustomerResponseDTO createResponseDto(Long id){
        return new CustomerResponseDTO(id,"Ivan","Ivanov","Ivanov","ivanov@gmail.com", CustomerType.REGULAR, null);
    }

    private CustomerRequestDTO createRequestDto(){
        return new CustomerRequestDTO("Ivan","Ivanov","Ivanov","ivanov@gmail.com", CustomerType.REGULAR);
    }

}