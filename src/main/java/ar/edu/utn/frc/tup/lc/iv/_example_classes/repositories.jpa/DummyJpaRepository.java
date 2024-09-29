package ar.edu.utn.frc.tup.lc.iv._example_classes.repositories.jpa;

import ar.edu.utn.frc.tup.lc.iv._example_classes.entities.DummyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DummyJpaRepository extends JpaRepository<DummyEntity, Long> {
    Optional<DummyEntity> findByDummy(String something);
}
  