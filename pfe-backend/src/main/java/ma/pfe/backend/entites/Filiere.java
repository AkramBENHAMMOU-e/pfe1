package ma.pfe.backend.entites;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Filiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID ;
    private String nom;
    private int nbrEtd;
    @OneToMany(mappedBy = "filiere", cascade = CascadeType.REMOVE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Seance> seanceList;
    @OneToMany(mappedBy = "filiere" , fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Salle> salles;

    public String toString() {
        return "Filiere{" +
                "name='" + nom + '\'' +
                '}';
    }
}
