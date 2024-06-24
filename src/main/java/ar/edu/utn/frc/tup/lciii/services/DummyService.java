package ar.edu.utn.frc.tup.lciii.services;

import java.util.List;

import ar.edu.utn.frc.tup.lciii.dtos.dummy.ResponseDummyDTO;
import ar.edu.utn.frc.tup.lciii.dtos.dummy.SaveDummyDTO;
import org.springframework.stereotype.Service;

@Service
public interface DummyService {
    List<ResponseDummyDTO> getDummyList();
    ResponseDummyDTO getDummyById(Long id);
    ResponseDummyDTO createDummy(SaveDummyDTO dummy) throws IllegalArgumentException;
    ResponseDummyDTO updateDummy(Long id, SaveDummyDTO dummy);
    void deleteDummyById(Long id);
}
  