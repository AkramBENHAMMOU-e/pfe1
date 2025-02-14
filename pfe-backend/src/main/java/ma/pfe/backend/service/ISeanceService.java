package ma.pfe.backend.service;

import ma.pfe.backend.entites.Salle;
import ma.pfe.backend.entites.Seance;

import java.util.List;

public interface ISeanceService {
    List<Seance> getSeances();
    Seance addSeance(Seance seance);
    List<Seance> getEmploisParFiliere(String nomFil);
    Seance getSeanceById(Long id);
    void deleteSeance(String nom);
    Seance modifierSeance(Long id, Seance seance);
    void refreshSeancesIds();


}
