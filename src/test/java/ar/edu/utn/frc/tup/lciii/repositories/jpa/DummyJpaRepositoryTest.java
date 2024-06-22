package ar.edu.utn.frc.tup.lciii.repositories.jpa;

import ar.edu.utn.frc.tup.lciii.entities.DummyEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DummyJpaRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DummyJpaRepository dummyJpaRepository;

    @Test
    public void findByDummy() {
        DummyEntity dummyEntity = new DummyEntity();
        dummyEntity.setDummy("something");

        entityManager.persistAndFlush(dummyEntity);

        Optional<DummyEntity> dummyResult = dummyJpaRepository.findByDummy("something");

        assertTrue(dummyResult.isPresent());
        assertEquals("something", dummyResult.get().getDummy());
    }
}