package ma.pfe.backend.service;

import lombok.AllArgsConstructor;
import ma.pfe.backend.entites.Filiere;
import ma.pfe.backend.entites.Seance;
import ma.pfe.backend.repository.SeanceRepos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@AllArgsConstructor
public class SeanceService implements ISeanceService{
    SeanceRepos seanceRepos;


    @Override
    public List<Seance> getSeances() {
        refreshSeancesIds();
        return seanceRepos.findAll();
    }

    @Override
    public Seance addSeance(Seance seance) {
        //System.out.println("bbbbbbbbbbbbb: " + seance.getNumberOfStudents());

        System.out.println("Saving Seance: " + seance);
        return seanceRepos.save(seance);
    }

    @Override
    public List<Seance> getEmploisParFiliere(String nomFil) {
        return seanceRepos.getEmploiByFiliere(nomFil);
    }

    @Override
    public Seance getSeanceById(Long id) {
        return seanceRepos.findById(id).orElseThrow(() -> new RuntimeException("Cette seance n'existe pas"));
    }

    @Override
    public void deleteSeance(String nom) {
        seanceRepos.deleteSeanceBynom(nom);
        refreshSeancesIds();
    }

    @Override
    public Seance modifierSeance(Long id, Seance seance) {
        seance.setId(id);
        return seanceRepos.save(seance);
    }


    @Transactional
    public void refreshSeancesIds() {
        try {

            List<Seance> seances = seanceRepos.findAllByOrderById();
            System.out.println("Filieres récupérées: " + seances);

            seanceRepos.disableForeignKeyChecks();
            System.out.println("Contraintes de clés étrangères désactivées");

            long newId = 1;
            for (Seance seance : seances) {
                seanceRepos.updateFiliereId(seance.getId(), newId++);
                System.out.println("Filiere ID mis à jour: " + seance.getId() + " à " + (newId - 1));
            }

            seanceRepos.enableForeignKeyChecks();
            System.out.println("Contraintes de clés étrangères réactivées");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour des IDs: " + e.getMessage());
        }
    }
}
