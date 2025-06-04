package ma.pfe.backend.entites;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.pfe.backend.enumeration.TypeSalle;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomSalle;
    private int capacite;
    @Enumerated(EnumType.STRING)
    private TypeSalle typeSalle;

    @OneToMany(mappedBy = "salle", fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Seance> seance;

    @ManyToOne
    private Filiere filiere;
}
