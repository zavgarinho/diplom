package com.zavga.diplom.controller.worker;

import com.zavga.diplom.dto.worker.WorkerRequestDTO;
import com.zavga.diplom.dto.worker.WorkerResponseDTO;
import com.zavga.diplom.service.worker.WorkerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workers")
public class WorkerController {

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping
    public ResponseEntity<List<WorkerResponseDTO>> findAll() {
        return ResponseEntity.ok(workerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkerResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(workerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<WorkerResponseDTO> create(@RequestBody WorkerRequestDTO requestDTO) {
        return ResponseEntity.ok(workerService.create(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkerResponseDTO> update(@PathVariable Long id, @RequestBody WorkerRequestDTO requestDTO) {
        return ResponseEntity.ok(workerService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var result = workerService.deleteById(id);
        return result ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}