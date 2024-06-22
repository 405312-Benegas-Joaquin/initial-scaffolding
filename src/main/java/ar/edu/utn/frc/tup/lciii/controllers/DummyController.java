package ar.edu.utn.frc.tup.lciii.controllers;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frc.tup.lciii.dtos.dummy.ResponseDummyDTO;
import ar.edu.utn.frc.tup.lciii.dtos.dummy.SaveDummyDTO;
import ar.edu.utn.frc.tup.lciii.entities.DummyEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ar.edu.utn.frc.tup.lciii.models.Dummy;
import ar.edu.utn.frc.tup.lciii.services.DummyService;

@RestController
@RequestMapping("/dummy")
public class DummyController {
    @Autowired
    private DummyService dummyService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ResponseDummyDTO>> getDummyList() {
        List<Dummy> dummyList = dummyService.getDummyList();

        List<ResponseDummyDTO> responseDummyList = new ArrayList<>();
        for (Dummy dummy : dummyList) {
            responseDummyList.add(modelMapper.map(dummy, ResponseDummyDTO.class));
        }

        return ResponseEntity.ok(responseDummyList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDummyDTO> getDummyById(@PathVariable Long id) {
        ResponseDummyDTO responseDummyDTO = modelMapper.map(dummyService.getDummyById(id), ResponseDummyDTO.class);
        return ResponseEntity.ok(responseDummyDTO);
    }

    @PostMapping
    public ResponseEntity<ResponseDummyDTO> createDummy(@RequestBody SaveDummyDTO saveDummyDTO) {
        Dummy dummy = modelMapper.map(saveDummyDTO, Dummy.class);
        ResponseDummyDTO responseDummyDTO = modelMapper.map(dummyService.createDummy(dummy), ResponseDummyDTO.class);
        return ResponseEntity.ok(responseDummyDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDummyDTO> updateDummy(@PathVariable Long id, @RequestBody SaveDummyDTO saveDummyDTO) {
        Dummy dummy = modelMapper.map(saveDummyDTO, Dummy.class);
        dummy.setId(id);
        ResponseDummyDTO responseDummyDTO = modelMapper.map(dummyService.updateDummy(dummy), ResponseDummyDTO.class);
        return ResponseEntity.ok(responseDummyDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteDummyById(@PathVariable Long id) {
        dummyService.deleteDummyById(id);
    }
}
  