package ar.edu.utn.frc.tup.lciii.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.tup.lciii.entities.DummyEntity;

@Repository
public interface DummyJpaRepository extends JpaRepository<DummyEntity, Long> {
}
  