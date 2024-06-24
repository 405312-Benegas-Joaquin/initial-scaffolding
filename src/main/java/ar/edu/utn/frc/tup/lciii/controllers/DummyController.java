package ar.edu.utn.frc.tup.lciii.controllers;

import java.util.List;

import ar.edu.utn.frc.tup.lciii.dtos.dummy.ResponseDummyDTO;
import ar.edu.utn.frc.tup.lciii.dtos.dummy.SaveDummyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ar.edu.utn.frc.tup.lciii.services.DummyService;

@RestController
@RequestMapping("/dummy")
public class DummyController {
    @Autowired
    private DummyService dummyService;

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
}
  