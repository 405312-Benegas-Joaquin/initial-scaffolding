package ar.edu.utn.frc.tup.lc.iv._example_classes.clients;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DummyDto(
        Long id,
        String dummy
) {
}
