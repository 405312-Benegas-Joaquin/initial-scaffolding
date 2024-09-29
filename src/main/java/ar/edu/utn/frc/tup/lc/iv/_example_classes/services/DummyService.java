package ar.edu.utn.frc.tup.lc.iv._example_classes.services;

import ar.edu.utn.frc.tup.lc.iv._example_classes.dtos.ResponseDummyDTO;
import ar.edu.utn.frc.tup.lc.iv._example_classes.dtos.SaveDummyDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DummyService {
    List<ResponseDummyDTO> getDummyList();

    ResponseDummyDTO getDummyById(Long id);

    ResponseDummyDTO createDummy(SaveDummyDTO dummy) throws IllegalArgumentException;

    ResponseDummyDTO updateDummy(Long id, SaveDummyDTO dummy);

    void deleteDummyById(Long id);

    ResponseDummyDTO getDummyBySomething(String something);
}
