package ma.pfe.backend.AlgGen;

import ma.pfe.backend.entites.Filiere;
import ma.pfe.backend.entites.Salle;
import ma.pfe.backend.entites.Seance;
import ma.pfe.backend.enumeration.Periode;

import java.time.DayOfWeek;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Contraintes {
    EmploisTp emploisTp;
    public Contraintes(EmploisTp emploisTp) {
        this.emploisTp = emploisTp;
    }
    public int checkIfSeancesMPeriodeEtJour(){
        int x = 0;
        List<Filiere> filieres = emploisTp.getFilieres();
        for (Filiere filiere : filieres){
            List<Seance> seances = filiere.getSeanceList();
            if (seances!=null) {
                for (Seance seance : seances) {
                    boolean isTrue = seances.stream()
                            .noneMatch(elementCompar ->
                                    seance.getJour() == elementCompar.getJour() &&
                                            elementCompar.getPeriode() == seance.getPeriode() &&
                                            elementCompar.getId() != seance.getId()
                            );
                    if(isTrue){
                        x++;
                    }


                }
            }
        }
        return x;
    }

    public int memeJourMemePeriode(Seance seance1, Seance seance2) {

        if (seance1.getJour().equals(seance2.getJour()) &&
                seance1.getPeriode().equals(seance2.getPeriode()) &&
                !seance1.getId().equals(seance2.getId())) {
            return 1;
        }

        return 0;
    }
    public int samediApresMidiVide(){
        int x =0;
        List<Seance> seances = emploisTp.getSeances();
        for (Seance seance : seances) {
            if(seance.getJour().equals(DayOfWeek.SATURDAY)){
                if (seance.getPeriode() == Periode.P3 || seance.getPeriode() == Periode.P4 || seance.getPeriode() == Periode.P5){
                    x++;
                }
            }

        }
        return x;
    }
    public int capaciteSalle() {
        int x = 0;
        List<Seance> seances = emploisTp.getSeances();

        for (Seance seance : seances) {
            Salle salle = seance.getSalle();
            if (salle != null && seance.getNumberOfStudents() > salle.getCapacite()) {
                x++;
            }
        }

        return x;
    }

    public int seancesProches() {
        int x = 0;
        List<Seance> seances = emploisTp.getSeances();

        for (Seance seance1 : seances) {
            for (Seance seance2 : seances) {
                if (memeJourMemePeriode(seance1, seance2) == 1) {
                    int periode1 = extractNumeroPeriode(String.valueOf(seance1.getPeriode()));
                    int periode2 = extractNumeroPeriode(String.valueOf(seance2.getPeriode()));
                    if (Math.abs(periode1 - periode2) > 1) {
                        x++;
                    }
                }
            }
        }

        return x;
    }

    public int eviteDernierePeriode(){
        int x=0;
        List<Seance> seances = emploisTp.getSeances();
        for (Seance seance : seances) {
            if (seance.getPeriode().equals(Periode.P5)||seance.getPeriode().equals(Periode.P4)) {
                x++;
            }
        }
        return x;
    }



    private int extractNumeroPeriode(String periode) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(periode);

        if (matcher.find()) {
            String numero = matcher.group();
            return Integer.parseInt(numero);
        } else {
            return 0;
        }
    }

}