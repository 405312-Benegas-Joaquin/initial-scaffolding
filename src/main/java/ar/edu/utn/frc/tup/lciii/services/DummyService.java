package ar.edu.utn.frc.tup.lciii.services;

import java.util.List;
import ar.edu.utn.frc.tup.lciii.models.Dummy;
import org.springframework.stereotype.Service;

@Service
public interface DummyService {
    List<Dummy> getDummyList();
    Dummy getDummyById(Long id);
    Dummy createDummy(Dummy dummy);
    Dummy updateDummy(Dummy dummy);
    void deleteDummyById(Long id);
}
  