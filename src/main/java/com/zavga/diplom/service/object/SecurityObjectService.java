package com.zavga.diplom.service.object;


import com.zavga.diplom.dto.object.SecurityObjectMapper;
import com.zavga.diplom.dto.object.SecurityObjectRequestDTO;
import com.zavga.diplom.dto.object.SecurityObjectResponseDTO;
import com.zavga.diplom.repository.customer.CustomerRepository;
import com.zavga.diplom.repository.equipment.EquipmentRepository;
import com.zavga.diplom.repository.object.SecurityObjectRepository;
import com.zavga.diplom.repository.work.InstallationWorkRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class SecurityObjectService {

    private final SecurityObjectRepository securityObjectRepository;
    private final SecurityObjectMapper mapper;
    private final CustomerRepository customerRepository;
    private final InstallationWorkRepository workRepository;
    private final EquipmentRepository equipmentRepository;

    public SecurityObjectService(SecurityObjectRepository securityObjectRepository, SecurityObjectMapper mapper,
                                 CustomerRepository customerRepository,
                                 InstallationWorkRepository workRepository,
                                 EquipmentRepository equipmentRepository){
        this.securityObjectRepository = securityObjectRepository;
        this.mapper = mapper;
        this.customerRepository = customerRepository;
        this.workRepository = workRepository;
        this.equipmentRepository = equipmentRepository;
    }

    public List<SecurityObjectResponseDTO> getAll(){
        var objects = this.securityObjectRepository.findAll();
        return objects.stream().map(o -> mapper.toResponseDto(o)).toList();
    }

    public Optional<SecurityObjectResponseDTO> getById(Long id){
        var object = this.securityObjectRepository.findById(id);
        if(object.isEmpty()){
            return Optional.empty();
        }

        SecurityObjectResponseDTO dto = mapper.toResponseDto(object.get());
        return Optional.of(dto);
    }
    @Transactional
    public SecurityObjectResponseDTO saveObject(SecurityObjectRequestDTO objectRequestDTO){
        var object = mapper.toEntity(objectRequestDTO);
        var customer = customerRepository.findById(objectRequestDTO.customerId()).orElseThrow();
        object.setCustomer(customer);
        // Я сначала создаю общую карточку объекта, так что в запросе не может быть сразу работ и обладнання
//        if(objectRequestDTO.worksIds() != null && !objectRequestDTO.worksIds().isEmpty()){
//            var works = new HashSet<>(workRepository.findAllById(objectRequestDTO.worksIds()));
//            if(!works.isEmpty())
//                object.setWorks(works);
//        }
        var savedObject = securityObjectRepository.save(object);
        return mapper.toResponseDto(savedObject);
    }
    @Transactional
    public SecurityObjectResponseDTO updateObject(Long id, SecurityObjectRequestDTO requestDTO){
        var existingObject = securityObjectRepository.findById(id).orElseThrow();
        var objectToUpdate = mapper.toEntity(requestDTO);
        objectToUpdate.setId(id);

        if(requestDTO.customerId() != null){
            var customer = customerRepository.findById(requestDTO.customerId()).orElseThrow();
            objectToUpdate.setCustomer(customer);
        }

        var updatedObject = securityObjectRepository.save(objectToUpdate);

        if(requestDTO.worksIds() != null && !requestDTO.worksIds().isEmpty()){
            existingObject.getWorks().forEach(w -> w.setObject(null));
            workRepository.saveAll(existingObject.getWorks());

            var works = workRepository.findAllById(requestDTO.worksIds());
            works.forEach(w -> w.setObject(updatedObject));
            workRepository.saveAll(works);
            updatedObject.setWorks(new HashSet<>(works));
        }

        if(requestDTO.equipmentIds() != null && !requestDTO.equipmentIds().isEmpty()){
            existingObject.getEquipment().forEach(e -> e.setObject(null));
            equipmentRepository.saveAll(existingObject.getEquipment());

            var equipment = equipmentRepository.findAllById(requestDTO.equipmentIds());
            equipment.forEach(e -> e.setObject(updatedObject));
            equipmentRepository.saveAll(equipment);
            updatedObject.setEquipment(new HashSet<>(equipment));
        }

        return mapper.toResponseDto(updatedObject);
    }
    @Transactional
    public void deleteObject(Long id){
        this.securityObjectRepository.deleteById(id);
    }
}
