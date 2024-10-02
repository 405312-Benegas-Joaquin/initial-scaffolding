package ar.edu.utn.frc.tup.lc.iv._example_classes.controllers;

import ar.edu.utn.frc.tup.lc.iv._example_classes.dtos.ResponseDummyDTO;
import ar.edu.utn.frc.tup.lc.iv._example_classes.dtos.SaveDummyDTO;
import ar.edu.utn.frc.tup.lc.iv._example_classes.services.DummyService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dummy")
@CrossOrigin(origins = "*")
public class DummyController {
    @Autowired
    private DummyService dummyService;

    private static final String RESILIENCE4J_INSTANCE_NAME = "getDummyList";
    private static final String FALLBACK_METHOD_NAME = "fallbackMethod";

    @GetMapping
    @CircuitBreaker(name = RESILIENCE4J_INSTANCE_NAME, fallbackMethod = FALLBACK_METHOD_NAME)
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

    public ResponseEntity<ResponseDummyDTO> fallbackForGetDummyById(Long dummyId, Throwable throwable) {
        ResponseDummyDTO defaultDummy = new ResponseDummyDTO();
        return ResponseEntity.ok(defaultDummy);
    }

    public ResponseEntity<ResponseDummyDTO> fallbackForCreateDummy(ResponseDummyDTO dummyDto, Throwable throwable) {
        ResponseDummyDTO defaultDummy = new ResponseDummyDTO();
        return ResponseEntity.ok(defaultDummy);
    }

    public ResponseEntity<ResponseDummyDTO> fallbackForUpdateDummy(Long dummyId, ResponseDummyDTO dummyDto, Throwable throwable) {
        ResponseDummyDTO defaultDummy = new ResponseDummyDTO();
        return ResponseEntity.ok(defaultDummy);
    }

    public ResponseEntity<Void> fallbackForDeleteDummyById(Long dummyId, Throwable throwable) {
        return ResponseEntity.noContent().build();
    }
}
  