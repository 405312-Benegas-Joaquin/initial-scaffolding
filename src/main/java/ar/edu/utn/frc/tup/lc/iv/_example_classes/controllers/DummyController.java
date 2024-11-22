package ar.edu.utn.frc.tup.lc.iv._example_classes.controllers;

import ar.edu.utn.frc.tup.lc.iv._example_classes.dtos.ResponseDummyDTO;
import ar.edu.utn.frc.tup.lc.iv._example_classes.dtos.SaveDummyDTO;
import ar.edu.utn.frc.tup.lc.iv._example_classes.services.DummyService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dummies")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DummyController {

    private final DummyService dummyService;

    @GetMapping
    public ResponseEntity<List<ResponseDummyDTO>> getDummyList() {
        return ResponseEntity.ok(dummyService.getDummyList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDummyDTO> getDummyById(@PathVariable Long id) {
        return ResponseEntity.ok(dummyService.getDummyById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseDummyDTO> createDummy(@RequestBody SaveDummyDTO saveDummyDTO) {
        return ResponseEntity.ok(dummyService.createDummy(saveDummyDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDummyDTO> updateDummy(@PathVariable Long id, @RequestBody SaveDummyDTO saveDummyDTO) {
        return ResponseEntity.ok(dummyService.updateDummy(id, saveDummyDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteDummyById(@PathVariable Long id) {
        dummyService.deleteDummyById(id);
    }

    public ResponseEntity<List<ResponseDummyDTO>> fallbackForGetAllDummies(Throwable throwable) {
        return ResponseEntity.ok(List.of());
    }
}
  