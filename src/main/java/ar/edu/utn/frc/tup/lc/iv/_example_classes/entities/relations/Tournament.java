package ar.edu.utn.frc.tup.lc.iv._example_classes.entities.relations;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "TOURNAMENTS")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "TOURNAMENTS_TEAMS",  // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "tournament_id"), // Clave externa a la tabla actual (Tournament)
            inverseJoinColumns = @JoinColumn(name = "team_id") // Clave externa a la tabla relacionada (Team)
    )
    private List<Team> teams;
}
