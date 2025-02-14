package ma.pfe.backend.service;

import lombok.AllArgsConstructor;
import ma.pfe.backend.entites.Filiere;
import ma.pfe.backend.entites.Salle;
import ma.pfe.backend.repository.SalleRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@AllArgsConstructor
public class SalleService implements ISalleService{
    @Autowired
SalleRepos salleRepos;

    @Override
    public List<Salle> getSalles() {

        refreshSallesIds();
        return salleRepos.findAll();
    }

    @Override
    public Salle addSalle(Salle salle) {
        return salleRepos.save(salle);
    }

    @Override
    public Salle getSalleById(Long id) {
        return salleRepos.findById(id).orElseThrow(() -> new RuntimeException("Cette filiere n'existe pas"));
    }

    @Override
    public void deleteSalleByNom(String nom) {
        salleRepos.deleteSalleByNom(nom);
        refreshSallesIds();
    }

    @Override
    public Salle modifierSalle(Long id, Salle salle) {
        salle.setId(id);
        return salleRepos.save(salle);
    }

    @Transactional
        public void refreshSallesIds() {
            try {

                List<Salle> salles = salleRepos.findAllByOrderById();
                System.out.println("Filieres récupérées: " + salles);

                salleRepos.disableForeignKeyChecks();
                System.out.println("Contraintes de clés étrangères désactivées");

                long newId = 1;
                for (Salle salle : salles) {
                    salleRepos.updateFiliereId(salle.getId(), newId++);
                    System.out.println("Filiere ID mis à jour: " + salle.getId() + " à " + (newId - 1));
                }

                salleRepos.enableForeignKeyChecks();
                System.out.println("Contraintes de clés étrangères réactivées");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Erreur lors de la mise à jour des IDs: " + e.getMessage());
            }

    }

}
