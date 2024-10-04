package ar.edu.utn.frc.tup.lc.iv._example_classes.entities.relations;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "TEAMS")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    /**
     * La anotación mappedBy se utiliza en la relación inversa
     * para indicar que otra entidad ya gestiona la relación y
     * especifica el nombre del campo que maneja la relación
     * en esa otra entidad.
     */
    @OneToMany(mappedBy = "team") // @OneToMany siempre se indica con mappedBy
    private List<Player> players;

    @ManyToMany(mappedBy = "teams")
    private List<Tournament> tournaments;
}
