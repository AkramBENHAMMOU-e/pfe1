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
        contrainteInsatisf += contraintes.capaciteSalle();
        contrainteInsatisf += contraintes.samediApresMidiVide();
        contrainteInsatisf += contraintes.seancesProches();
        contrainteInsatisf += contraintes.eviteDernierePeriode();
        contrainteInsatisf += contraintes.checkIfSeancesMPeriodeEtJour();

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
        for (Filiere filiere : getFilieres()) {
            salles.add((Salle) filiere.getSalles());

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

}