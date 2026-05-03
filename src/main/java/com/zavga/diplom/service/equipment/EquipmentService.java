package com.zavga.diplom.service.equipment;


import com.zavga.diplom.dto.equipment.EquipmentMapper;
import com.zavga.diplom.dto.equipment.EquipmentRequestDTO;
import com.zavga.diplom.dto.equipment.EquipmentResponseDTO;
import com.zavga.diplom.repository.equipment.EquipmentRepository;
import com.zavga.diplom.repository.object.SecurityObjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final SecurityObjectRepository objectRepository;
    private final EquipmentMapper mapper;

    public EquipmentService(EquipmentRepository equipmentRepository,
                            EquipmentMapper mapper,
                            SecurityObjectRepository objectRepository){
        this.equipmentRepository = equipmentRepository;
        this.mapper = mapper;
        this.objectRepository = objectRepository;
    }

    public List<EquipmentResponseDTO> findAll(){
        var equipment = this.equipmentRepository.findAll();
        if(equipment.isEmpty()){
            return List.of();
        }
        return equipment.stream().map(mapper::toResponseDTO).toList();

    }

    public Optional<EquipmentResponseDTO> findById(Long id){
        var equipment = this.equipmentRepository.findById(id);
        return equipment.map(mapper::toResponseDTO);
    }

    @Transactional
    public EquipmentResponseDTO save(EquipmentRequestDTO requestDTO){
        var equipmentToSave = mapper.toEntity(requestDTO);
        if (requestDTO.objectId() !=
                null) {
            var object = objectRepository.findById(requestDTO.objectId()).orElseThrow();
            equipmentToSave.setObject(object);
        }

        var savedEquipment = this.equipmentRepository.save(equipmentToSave);
        return mapper.toResponseDTO(savedEquipment);
    }
    @Transactional
    public EquipmentResponseDTO update(Long id,EquipmentRequestDTO requestDTO){
        var current = equipmentRepository.findById(id).orElseThrow();
        var equipmentToSave = mapper.toEntity(requestDTO);
        equipmentToSave.setId(id);
        var currentObjectId = current.getObject() != null ? current.getObject().getId() : null;
        if (requestDTO.objectId() != null && !requestDTO.objectId().equals(currentObjectId)) {
            var object = objectRepository.findById(requestDTO.objectId()).orElseThrow();
            equipmentToSave.setObject(object);
        } else {
            equipmentToSave.setObject(current.getObject());
        }
        var updatedEquipment = equipmentRepository.save(equipmentToSave);
        return mapper.toResponseDTO(updatedEquipment);


    }

    @Transactional
    public boolean delete(Long id){
       return equipmentRepository.deleteByIdWithCount(id) > 0;
    }
}
