package ar.edu.utn.frc.tup.lc.iv._example_classes.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDummyDTO {
    private Long id;
    private String dummy;
}
