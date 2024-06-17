package ar.edu.utn.frc.tup.lciii.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.tup.lciii.models.Dummy;
import ar.edu.utn.frc.tup.lciii.repositories.jpa.DummyJpaRepository;
import ar.edu.utn.frc.tup.lciii.services.DummyService;
import ar.edu.utn.frc.tup.lciii.entities.DummyEntity;

@Service
public class DummyServiceImpl implements DummyService {
    @Autowired
    private DummyJpaRepository dummyJpaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Dummy> getDummyList() {
        List<DummyEntity> dummyEntities = dummyJpaRepository.findAll();

        List<Dummy> dummyList = new ArrayList<>();

        for (DummyEntity dummyEntity : dummyEntities) {
            dummyList.add(modelMapper.map(dummyEntity, Dummy.class));
        }
        
        return dummyList;
    }

    @Override
    public Dummy getDummyById(Long id) {
        DummyEntity dummyEntity = dummyJpaRepository.getReferenceById(id);
        return modelMapper.map(dummyEntity, Dummy.class);
    }

    @Override
    public Dummy createDummy(Dummy dummy) {
        // Optional<DummyEntity> DummyEntityFound = dummyJpaRepository.findBySomething();

        // if (DummyEntityFound.isPresent()) {
        //     return null;
        // }

        DummyEntity dummyEntity = modelMapper.map(dummy, DummyEntity.class);
        DummyEntity DummyEntitySaved = dummyJpaRepository.save(dummyEntity);

        return modelMapper.map(DummyEntitySaved, Dummy.class);
    }

    @Override
    public Dummy updateDummy(Dummy dummy) {
        DummyEntity dummyEntity = modelMapper.map(dummy, DummyEntity.class);
        DummyEntity DummyEntitySaved = dummyJpaRepository.save(dummyEntity);

        return modelMapper.map(DummyEntitySaved, Dummy.class);
    }

    @Override
    public void deleteDummyById(Long id) {
        dummyJpaRepository.deleteById(id);
    }
}
  