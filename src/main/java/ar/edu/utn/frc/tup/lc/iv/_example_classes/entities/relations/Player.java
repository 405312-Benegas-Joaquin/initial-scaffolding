package ar.edu.utn.frc.tup.lc.iv._example_classes.entities.relations;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PLAYERS")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "team_id") // Acá especificamos el nombre de la clave foránea en la tabla PLAYERS
    // @ManyToOne siempre se indica con @JoinColumn y el nombre del campo
    private Team team;
}
