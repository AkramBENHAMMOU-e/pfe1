package ma.pfe.backend.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.pfe.backend.enumeration.Periode;

import java.time.DayOfWeek;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Seance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomSeance;
    private int numberOfStudents;
    @Enumerated(EnumType.STRING)
    private Periode periode;
    @Enumerated(EnumType.STRING)
    private DayOfWeek jour;
    @ManyToOne
    private Filiere filiere;
    @ManyToOne
    private Salle salle;
    

    public String toString() {
        return "{" +
                "name='" + nomSeance + '\'' +
                '}';
    }
}
