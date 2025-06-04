package ma.pfe.backend.AlgGen;

import lombok.Getter;
import ma.pfe.backend.entites.Filiere;
import ma.pfe.backend.entites.Salle;
import ma.pfe.backend.entites.Seance;
import ma.pfe.backend.enumeration.Periode;

import java.io.File;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class EmploisTp {
    List<List<Seance>> EmploiTp;
    @Getter
    double fitness;


    public EmploisTp(int nbrFiliere) {
        this.EmploiTp = new ArrayList<>(nbrFiliere);
        for (int i = 0; i < nbrFiliere; i++) {
            this.EmploiTp.add(new ArrayList<>());
        }
    }

    public int getNbrFilieres() {
        return EmploiTp.size();
    }

    public List<Filiere> getFilieres() {
        List<Filiere> filieres = new ArrayList<>();
        for (List<Seance> emploiTp : EmploiTp) {
            for (Seance seance : emploiTp) {
                Filiere filiere = seance.getFiliere();
                if (filiere != null && !filieres.contains(filiere)) {
                    filieres.add(filiere);

                }

            }
        }
        return filieres;
    }

    public double calculateFitness() {
        Contraintes contraintes = new Contraintes(this);
        int contrainteInsatisf = 0;
        contrainteInsatisf += contraintes.capaciteSalle() * 5;
        contrainteInsatisf += contraintes.samediApresMidiVide() * 10;
        contrainteInsatisf += contraintes.eviteDernierePeriode() * 5;
        contrainteInsatisf += contraintes.filiereSameDaySamePeriodConflict() * 100;
        contrainteInsatisf += contraintes.salleOccupeeMemePeriode() * 100;
        contrainteInsatisf += contraintes.maxSeancesParJourFiliere(5) * 5;
        contrainteInsatisf += contraintes.filiereModuleCount(6) * 50;
        contrainteInsatisf += contraintes.consecutiveSessions() * 5;
        contrainteInsatisf += contraintes.typeSeanceSalleMismatch() * 10;
        contrainteInsatisf += contraintes.preferentialFiliereSalleUsage() * 20;
        contrainteInsatisf += contraintes.maxSessionsPerDayPerSalle(5) * 50;

        double fitness = 1 / (double) (contrainteInsatisf + 1);

        return fitness;
    }

    public List<Seance> getSeances() {
        List<Seance> seances = new ArrayList<>();
        for (List<Seance> emploiTp : EmploiTp) {
            for (Seance seance : emploiTp) {
                if (!seances.contains(seance)) {
                    seances.add(seance);
                }
            }
        }
        return seances;
    }

    public List<Salle> getSalles() {
        List<Salle> salles = new ArrayList<>();
        for (List<Seance> filiereSchedule : EmploiTp) {
            for (Seance seance : filiereSchedule) {
                if (seance.getSalle() != null && !salles.contains(seance.getSalle())) {
                    salles.add(seance.getSalle());
                }
            }
        }
        return salles;
    }

    public void addSeance(int filiereIndex, Seance seance) {
        List<Seance> emploitp = EmploiTp.get(filiereIndex);
        emploitp.add(seance);

    }

    public List<Seance> getEmploiTp(int filierIndice) {
        return EmploiTp.get(filierIndice);
    }

    public void mutation(int filiereIndex, int pos1, int pos2) {
        List<Seance> emploTp = EmploiTp.get(filiereIndex);
        Seance gene1 = emploTp.get(pos1);
        Seance gene2 = emploTp.get(pos2);
        emploTp.set(pos1, gene2);
        emploTp.set(pos2, gene1);
    }

    // Nouvelle méthode pour vérifier les conflits avant d'ajouter une séance
    public boolean willCauseConflict(Seance newSeance, int filiereIndex) {
        // Vérifier le conflit de filière (même jour, même période, même filière)
        for (Seance existingSeance : this.getEmploiTp(filiereIndex)) {
            // Éviter de comparer la séance avec elle-même
            if (existingSeance.getId() != null && newSeance.getId() != null && existingSeance.getId().equals(newSeance.getId())) {
                continue;
            }
            if (existingSeance.getJour() == newSeance.getJour() &&
                existingSeance.getPeriode() == newSeance.getPeriode()) {
                return true;
            }
        }

        // Vérifier le conflit de salle (même jour, même période, même salle pour toutes les filières)
        for (List<Seance> existingFiliereSchedule : this.EmploiTp) {
            for (Seance existingSeance : existingFiliereSchedule) {
                // Éviter de comparer la séance avec elle-même
                if (existingSeance.getId() != null && newSeance.getId() != null && existingSeance.getId().equals(newSeance.getId())) {
                    continue;
                }
                if (existingSeance.getJour() == newSeance.getJour() &&
                    existingSeance.getPeriode() == newSeance.getPeriode() &&
                    existingSeance.getSalle() != null &&
                    newSeance.getSalle() != null &&
                    existingSeance.getSalle().getId().equals(newSeance.getSalle().getId())) {
                    return true;
                }
            }
        }

        // Vérifier le type de salle (si la salle choisie correspond au type de séance)
        if (newSeance.getSalle() != null && !newSeance.getTypeSeance().name().equals(newSeance.getSalle().getTypeSalle().name())) {
            return true;
        }

        // Vérifier la capacité de la salle
        if (newSeance.getSalle() != null && newSeance.getNumberOfStudents() > newSeance.getSalle().getCapacite()) {
            return true;
        }

        return false;
    }

}