package ar.edu.utn.frc.tup.lc.iv._example_classes.services.impl;

import ar.edu.utn.frc.tup.lc.iv._example_classes.dtos.ResponseDummyDTO;
import ar.edu.utn.frc.tup.lc.iv._example_classes.dtos.SaveDummyDTO;
import ar.edu.utn.frc.tup.lc.iv._example_classes.entities.DummyEntity;
import ar.edu.utn.frc.tup.lc.iv._example_classes.repositories.jpa.DummyJpaRepository;
import ar.edu.utn.frc.tup.lc.iv._example_classes.services.DummyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DummyServiceImpl implements DummyService {

    private final DummyJpaRepository dummyJpaRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<ResponseDummyDTO> getDummyList() {
        List<DummyEntity> dummyEntities = dummyJpaRepository.findAll();

        List<ResponseDummyDTO> dummyList = new ArrayList<>();

        for (DummyEntity dummyEntity : dummyEntities) {
            dummyList.add(modelMapper.map(dummyEntity, ResponseDummyDTO.class));
        }

        return dummyList;
    }

    @Override
    public ResponseDummyDTO getDummyById(Long id) {
        DummyEntity dummyEntity = dummyJpaRepository.getReferenceById(id);
        return modelMapper.map(dummyEntity, ResponseDummyDTO.class);
    }

    @Override
    public ResponseDummyDTO createDummy(SaveDummyDTO dummy) throws IllegalArgumentException {
        Optional<DummyEntity> dummyEntityFound = dummyJpaRepository.findByDummy(dummy.getDummy());

        if (dummyEntityFound.isPresent()) {
            throw new IllegalArgumentException("Dummy already exists");
        }

        DummyEntity dummyEntity = modelMapper.map(dummy, DummyEntity.class);
        DummyEntity dummyEntitySaved = dummyJpaRepository.save(dummyEntity);

        return modelMapper.map(dummyEntitySaved, ResponseDummyDTO.class);
    }

    @Override
    public ResponseDummyDTO updateDummy(Long id, SaveDummyDTO dummy) {
        DummyEntity dummyEntity = modelMapper.map(dummy, DummyEntity.class);
        dummyEntity.setId(id);
        DummyEntity dummyEntitySaved = dummyJpaRepository.save(dummyEntity);

        return modelMapper.map(dummyEntitySaved, ResponseDummyDTO.class);
    }

    @Override
    public void deleteDummyById(Long id) {
        dummyJpaRepository.deleteById(id);
    }

    @Override
    public ResponseDummyDTO getDummyBySomething(String something) {
        Optional<DummyEntity> dummyEntityFound = dummyJpaRepository.findByDummy(something);

        if (dummyEntityFound.isPresent()) {
            throw new IllegalArgumentException("Dummy already exists");
        }

        return modelMapper.map(dummyEntityFound, ResponseDummyDTO.class);
    }
}
