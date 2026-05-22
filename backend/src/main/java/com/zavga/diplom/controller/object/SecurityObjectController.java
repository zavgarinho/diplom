package com.zavga.diplom.controller.object;


import com.zavga.diplom.dto.object.SecurityObjectRequestDTO;
import com.zavga.diplom.dto.object.SecurityObjectResponseDTO;
import com.zavga.diplom.entity.customer.CustomerType;
import com.zavga.diplom.entity.object.ObjectStatus;
import com.zavga.diplom.entity.object.ObjectType;
import com.zavga.diplom.service.object.SecurityObjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/objects")
@CrossOrigin("*")
public class SecurityObjectController {

    private final SecurityObjectService securityObjectService;

    public SecurityObjectController(SecurityObjectService securityObjectService){
        this.securityObjectService = securityObjectService;
    }

    @GetMapping
    public ResponseEntity<List<SecurityObjectResponseDTO>> getAll(){
        return ResponseEntity.ok(this.securityObjectService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecurityObjectResponseDTO> getById(@PathVariable Long id){
        Optional<SecurityObjectResponseDTO> object = this.securityObjectService.getById(id);
        if(object.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(object.get());
    }

    @PostMapping
    public ResponseEntity<SecurityObjectResponseDTO> createObject(@RequestBody SecurityObjectRequestDTO objectRequestDTO){
        return new ResponseEntity<>(this.securityObjectService.saveObject(objectRequestDTO), HttpStatus.CREATED);

    }
    @PutMapping("/{id}")
    public ResponseEntity<SecurityObjectResponseDTO> updateObject(@PathVariable Long id, @RequestBody SecurityObjectRequestDTO requestDTO){
        return new ResponseEntity<>(this.securityObjectService.updateObject(id,requestDTO), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObject(@PathVariable Long id){
        this.securityObjectService.deleteObject(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/object-types")
    public ResponseEntity<Map<String,String>> getObjectTypes(){
        return ResponseEntity.ok(
                Arrays.stream(ObjectType.values())
                        .collect(Collectors.toMap(Enum::name, ObjectType::getLabel))
        );
    }

    @GetMapping("/object-statuses")
    public ResponseEntity<Map<String,String>> getObjectStatuses(){
        return ResponseEntity.ok(
                Arrays.stream(ObjectStatus.values())
                        .collect(Collectors.toMap(Enum::name, ObjectStatus::getLabel))
        );
    }
}
