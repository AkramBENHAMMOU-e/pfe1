package ma.pfe.backend.AlgGen;

import ma.pfe.backend.entites.Filiere;
import ma.pfe.backend.entites.Salle;
import ma.pfe.backend.entites.Seance;
import ma.pfe.backend.enumeration.Periode;

import java.time.DayOfWeek;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicInteger;

public class Contraintes {
    EmploisTp emploisTp;

    public Contraintes(EmploisTp emploisTp) {
        this.emploisTp = emploisTp;
    }

    // Contrainte 1: Une filière ne peut pas avoir deux séances le même jour et à la même période.
    public int filiereSameDaySamePeriodConflict() {
        int conflicts = 0;
        for (List<Seance> filiereSchedule : emploisTp.EmploiTp) { // Itérer à travers l'emploi du temps de chaque filière
            for (int i = 0; i < filiereSchedule.size(); i++) {
                for (int j = i + 1; j < filiereSchedule.size(); j++) {
                    Seance seance1 = filiereSchedule.get(i);
                    Seance seance2 = filiereSchedule.get(j);
                    if (seance1.getJour() == seance2.getJour() &&
                        seance1.getPeriode() == seance2.getPeriode()) {
                        conflicts++;
                    }
                }
            }
        }
        return conflicts;
    }

    // Fonction d'aide pour identifier les conflits (non utilisée directement dans le calcul de la fitness)
    public int memeJourMemePeriode(Seance seance1, Seance seance2) {
        if (seance1.getJour().equals(seance2.getJour()) &&
            seance1.getPeriode().equals(seance2.getPeriode()) &&
            !seance1.getId().equals(seance2.getId())) {
            return 1;
        }
        return 0;
    }

    // Contrainte 2: Éviter les séances le samedi après-midi (P3, P4, P5)
    public int samediApresMidiVide() {
        int x = 0;
        List<Seance> seances = emploisTp.getSeances();
        for (Seance seance : seances) {
            if (seance.getJour().equals(DayOfWeek.SATURDAY)) {
                if (seance.getPeriode() == Periode.P3 || seance.getPeriode() == Periode.P4 || seance.getPeriode() == Periode.P5) {
                    x++;
                }
            }
        }
        return x;
    }

    // Contrainte 3: La capacité de la salle ne doit pas être dépassée.
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

    // Contrainte 4: Une même salle ne peut pas être occupée par deux séances différentes le même jour et à la même période.
    public int salleOccupeeMemePeriode() {
        int conflicts = 0;
        List<Seance> allSeances = emploisTp.getSeances();

        for (int i = 0; i < allSeances.size(); i++) {
            for (int j = i + 1; j < allSeances.size(); j++) {
                Seance seance1 = allSeances.get(i);
                Seance seance2 = allSeances.get(j);

                if (seance1.getSalle() != null && seance2.getSalle() != null &&
                    seance1.getSalle().getId().equals(seance2.getSalle().getId()) &&
                    seance1.getJour().equals(seance2.getJour()) &&
                    seance1.getPeriode().equals(seance2.getPeriode())) {
                    conflicts++;
                }
            }
        }
        return conflicts;
    }

    // Contrainte 5: Éviter les dernières périodes (P4 et P5).
    public int eviteDernierePeriode() {
        int x = 0;
        List<Seance> seances = emploisTp.getSeances();
        for (Seance seance : seances) {
            if (seance.getPeriode().equals(Periode.P5) || seance.getPeriode().equals(Periode.P4)) {
                x++;
            }
        }
        return x;
    }

    // Contrainte 6: Nombre maximal de séances par jour pour une filière
    public int maxSeancesParJourFiliere(int maxSessionsPerDay) {
        int violations = 0;
        List<Filiere> filieres = emploisTp.getFilieres();

        for (Filiere filiere : filieres) { // Itérer à travers chaque filière
            List<Seance> filiereSchedule = emploisTp.getEmploiTp(filiere.getID().intValue() - 1); // Correction: Utiliser getID()
            for (DayOfWeek day : DayOfWeek.values()) {
                long sessionsOnDay = filiereSchedule.stream()
                    .filter(s -> s.getJour().equals(day))
                    .count();
                if (sessionsOnDay > maxSessionsPerDay) {
                    violations += (sessionsOnDay - maxSessionsPerDay);
                }
            }
        }
        return violations;
    }

    // Contrainte 7: Chaque filière doit avoir un nombre spécifique de modules (séances).
    public int filiereModuleCount(int expectedModulesPerFiliere) {
        int violations = 0;
        List<Filiere> filieres = emploisTp.getFilieres();

        for (Filiere filiere : filieres) {
            // Assurez-vous que l'index est valide (filiere.getID() est 1-indexé)
            int filiereIndex = filiere.getID().intValue() - 1;
            if (filiereIndex >= 0 && filiereIndex < emploisTp.EmploiTp.size()) {
                List<Seance> filiereSchedule = emploisTp.getEmploiTp(filiereIndex);
                if (filiereSchedule.size() != expectedModulesPerFiliere) {
                    violations += Math.abs(filiereSchedule.size() - expectedModulesPerFiliere);
                }
            } else {
                // Gérer le cas où l'index de la filière est hors limites (devrait être peu probable avec des données cohérentes)
                violations++; // Considérez cela comme une violation
            }
        }
        return violations;
    }

    // Contrainte 8: Les séances devraient être programmées consécutivement si possible.
    public int consecutiveSessions() {
        AtomicInteger violations = new AtomicInteger(0);
        List<Filiere> filieres = emploisTp.getFilieres();

        for (Filiere filiere : filieres) {
            List<Seance> filiereSchedule = emploisTp.getEmploiTp(filiere.getID().intValue() - 1);

            // Grouper les séances par jour
            filiereSchedule.stream()
                .collect(Collectors.groupingBy(Seance::getJour))
                .forEach((day, sessionsForDay) -> {
                    // Trier les séances du jour par période
                    sessionsForDay.sort((s1, s2) -> Integer.compare(extractNumeroPeriode(String.valueOf(s1.getPeriode())),
                                                                   extractNumeroPeriode(String.valueOf(s2.getPeriode()))));

                    // Vérifier les écarts entre les périodes consécutives
                    for (int i = 0; i < sessionsForDay.size() - 1; i++) {
                        int currentPeriodNum = extractNumeroPeriode(String.valueOf(sessionsForDay.get(i).getPeriode()));
                        int nextPeriodNum = extractNumeroPeriode(String.valueOf(sessionsForDay.get(i + 1).getPeriode()));

                        if (nextPeriodNum - currentPeriodNum > 1) {
                            // Il y a un écart non consécutif
                            violations.incrementAndGet();
                        }
                    }
                });
        }
        return violations.get();
    }

    // Contrainte 9: Le type de séance doit correspondre au type de salle
    public int typeSeanceSalleMismatch() {
        int violations = 0;
        List<Seance> allSeances = emploisTp.getSeances();

        for (Seance seance : allSeances) {
            if (seance.getSalle() != null && !seance.getTypeSeance().name().equals(seance.getSalle().getTypeSalle().name())) {
                violations++;
            }
        }
        return violations;
    }

    // Contrainte 10: Utilisation préférentielle des salles de la filière
    public int preferentialFiliereSalleUsage() {
        int violations = 0;
        List<Seance> allSeances = emploisTp.getSeances();

        for (Seance seance : allSeances) {
            if (seance.getSalle() != null && seance.getFiliere() != null) {
                // Si la salle est associée à une filière, vérifiez si c'est la bonne.
                if (seance.getSalle().getFiliere() != null &&
                    !seance.getSalle().getFiliere().getID().equals(seance.getFiliere().getID())) {
                    violations++;
                }
                // Si la salle n'est pas associée à une filière (salle générale comme un amphi), ce n'est pas une violation.
                // Nous ne pénalisons que l'utilisation d'une salle d'UNE AUTRE filière.
            }
        }
        return violations;
    }

    // Contrainte 11: Nombre maximal de séances par jour par salle
    public int maxSessionsPerDayPerSalle(int maxSessions) {
        int violations = 0;
        List<Salle> allSalles = emploisTp.getSalles();

        for (Salle salle : allSalles) {
            for (DayOfWeek day : DayOfWeek.values()) {
                long sessionsInSalleOnDay = emploisTp.getSeances().stream()
                    .filter(seance -> seance.getSalle() != null &&
                                       seance.getSalle().getId().equals(salle.getId()) &&
                                       seance.getJour().equals(day))
                    .count();
                if (sessionsInSalleOnDay > maxSessions) {
                    violations += (sessionsInSalleOnDay - maxSessions);
                }
            }
        }
        return violations;
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