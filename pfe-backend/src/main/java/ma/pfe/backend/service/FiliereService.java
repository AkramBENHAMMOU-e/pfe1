package ma.pfe.backend.service;

import lombok.AllArgsConstructor;
import ma.pfe.backend.entites.Filiere;
import ma.pfe.backend.repository.FiliereRepos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class FiliereService implements IFilierService {

    private final FiliereRepos filiereRepos;

    @Override
    public List<Filiere> getFilieres() {
        refreshFiliereIds();
        return filiereRepos.findAll();
    }

    @Override
    public Filiere getFiliereById(Long id) {
        return filiereRepos.findById(id).orElseThrow(() -> new RuntimeException("Cette filiere n'existe pas"));
    }

    @Override
    public Filiere getFiliereBynom(String nom) {
        return filiereRepos.getFiliereByName(nom);
    }

    @Override
    public Filiere addFiliere(Filiere filiere) {
        return filiereRepos.save(filiere);
    }

    @Override
    public Filiere modifierFiliere(Long id, Filiere filiere) {
        filiere.setID(id);
        return filiereRepos.save(filiere);
    }

    @Override
    public void deleteFiliereByNOm(String nom) {
        filiereRepos.deleteFiliereByNom(nom);
        refreshFiliereIds();
    }

    @Override
    public String deleteFiliereById(Long id) {
        try {
            getFiliereById(id);
            filiereRepos.deleteById(id);
            System.out.println("ok,supp");
            refreshFiliereIds();
            System.out.println("IDs après suppression et rafraîchissement: " + filiereRepos.findAllByOrderById());
            return "Votre suppression a été effectuée avec succès";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Transactional
    public void refreshFiliereIds() {
        try {

            List<Filiere> filieres = filiereRepos.findAllByOrderById();
            System.out.println("Filieres récupérées: " + filieres);

            filiereRepos.disableForeignKeyChecks();
            System.out.println("Contraintes de clés étrangères désactivées");

            long newId = 1;
            for (Filiere filiere : filieres) {
                filiereRepos.updateFiliereId(filiere.getID(), newId++);
                System.out.println("Filiere ID mis à jour: " + filiere.getID() + " à " + (newId - 1));
            }

            filiereRepos.enableForeignKeyChecks();
            System.out.println("Contraintes de clés étrangères réactivées");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour des IDs: " + e.getMessage());
        }
    }
}
