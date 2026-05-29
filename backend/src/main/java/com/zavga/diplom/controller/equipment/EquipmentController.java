package com.zavga.diplom.controller.equipment;

import com.zavga.diplom.dto.equipment.EquipmentRequestDTO;
import com.zavga.diplom.dto.equipment.EquipmentResponseDTO;
import com.zavga.diplom.entity.equipment.EquipmentType;
import com.zavga.diplom.service.equipment.EquipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/equipment")
@CrossOrigin("*")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService){
        this.equipmentService = equipmentService;
    }

    @GetMapping
    public ResponseEntity<List<EquipmentResponseDTO>> findAll(){
        var equipment = equipmentService.findAll();
        if(equipment.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(equipment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentResponseDTO> findById(@PathVariable Long id){
        var equipment = equipmentService.findById(id);
        if(equipment.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(equipment.get());
    }

    @PostMapping
    public ResponseEntity<EquipmentResponseDTO> createEquipment(@RequestBody EquipmentRequestDTO requestDTO){
        return ResponseEntity.ok(equipmentService.save(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipmentResponseDTO> updateEquipment(@PathVariable Long id, @RequestBody EquipmentRequestDTO requestDTO){
        return ResponseEntity.ok(equipmentService.update(id,requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Long id){
        return equipmentService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/equipment-types")
    public ResponseEntity<Map<String,String>> getEquipmentTypes(){
        return ResponseEntity.ok(Arrays.stream(EquipmentType.values())
                .collect(Collectors.toMap(Enum::name,EquipmentType::getLabel)));
    }
}
