package ma.pfe.backend.service;

import ma.pfe.backend.entites.Filiere;
import ma.pfe.backend.entites.Salle;

import java.util.List;

public interface ISalleService {
    List<Salle> getSalles();
    Salle addSalle(Salle salle);
    Salle getSalleById(Long id);
    void deleteSalleByNom(String nom);
    Salle modifierSalle(Long id, Salle salle);
    void refreshSallesIds();
}
