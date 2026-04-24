package com.zavga.diplom.controller.work;

import com.zavga.diplom.dto.work.InstallationWorkRequestDTO;
import com.zavga.diplom.dto.work.InstallationWorkResponseDTO;
import com.zavga.diplom.entity.work.InstallationWork;
import com.zavga.diplom.service.work.InstallationWorkService;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/works")
public class InstallationWorkController {

    private final InstallationWorkService workService;
    public InstallationWorkController(InstallationWorkService workService){
        this.workService = workService;
    }

    @GetMapping
    public ResponseEntity<List<InstallationWorkResponseDTO>> findAll(){
        return ResponseEntity.ok(workService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstallationWorkResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(workService.findById(id));
    }

    @PostMapping
    public ResponseEntity<InstallationWorkResponseDTO> create(@RequestBody InstallationWorkRequestDTO requestDTO){
        return ResponseEntity.ok(workService.create(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstallationWorkResponseDTO> update(@PathVariable Long id, @RequestBody InstallationWorkRequestDTO requestDTO){
        return ResponseEntity.ok(workService.update(id,requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        var result = workService.deleteById(id);
        return result ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
