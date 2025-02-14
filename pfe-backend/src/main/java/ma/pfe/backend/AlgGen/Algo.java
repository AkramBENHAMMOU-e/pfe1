package ma.pfe.backend.AlgGen;

import ma.pfe.backend.donnee.ImportData;
import ma.pfe.backend.entites.Filiere;
import ma.pfe.backend.entites.Seance;
import ma.pfe.backend.enumeration.Periode;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Algo {
    private  final int POPULATION_SIZE = 50;
    private  final double MUTATION_RATE = 0.3;
    private  final double CROSSOVER_RATE = 0.9;
    private  final int MAX_GENERATIONS = 70;
    Periode[] timePeriode;
    List<DayOfWeek> days;

    Random random;
    private List<EmploisTp> population;


    public Algo() {
        this.population = new ArrayList<>(POPULATION_SIZE);

        this.random=new Random();

        timePeriode = Periode.values();
        days = new ArrayList<>();
        days.add(DayOfWeek.MONDAY);
        days.add(DayOfWeek.TUESDAY);
        days.add(DayOfWeek.WEDNESDAY);
        days.add(DayOfWeek.THURSDAY);
        days.add(DayOfWeek.FRIDAY);
        days.add(DayOfWeek.SATURDAY);
    }

    public DayOfWeek getRandomDay() {
        int index = random.nextInt(days.size());
        return days.get(index);
    }

    public Periode getRandomPeriode(DayOfWeek day) {
        if (day.equals(DayOfWeek.SATURDAY)) {
            int i = random.nextInt(timePeriode.length);
            while (timePeriode[i].equals(Periode.P3) || timePeriode[i].equals(Periode.P4) || timePeriode[i].equals(Periode.P5)) {
                i = random.nextInt(timePeriode.length);
            }
            return timePeriode[i];
        }  if (day.equals(DayOfWeek.WEDNESDAY) ) {
            int i = random.nextInt(timePeriode.length);
            while (timePeriode[i].equals(Periode.P4) || timePeriode[i].equals(Periode.P5)){
                i = random.nextInt(timePeriode.length);
            }
            return timePeriode[i];

        }  if (day.equals(DayOfWeek.FRIDAY)) {
            int  i = random.nextInt(timePeriode.length);
            while (timePeriode[i].equals(Periode.P3) || timePeriode[i].equals(Periode.P5)){
                i = random.nextInt(timePeriode.length);
            }
            return timePeriode[i];

        } if ( day.equals(DayOfWeek.MONDAY)|| day.equals(DayOfWeek.THURSDAY)|| day.equals(DayOfWeek.TUESDAY)){
            int i = random.nextInt(timePeriode.length);
            while (timePeriode[i].equals(Periode.P5) ){
                i=random.nextInt(timePeriode.length);
            }
            return timePeriode[i];
        }
        else {
            int i = random.nextInt(timePeriode.length);
            return timePeriode[i];
        }



    }
    private List<Seance> getSeanceFiliere(Filiere filiere) {
        return filiere.getSeanceList();
    }

    public int getRandomSalle(Seance seance) {
        int nbrEtd = seance.getNumberOfStudents();
        int index = 0;
        for (int i = 0; i < ImportData.salles.size(); i++) {
            index = random.nextInt(ImportData.salles.size());
            if (ImportData.salles.get(index).getCapacite() >= nbrEtd)
                return index;
        }
        return index;
    }

    public void initializePopulation() {
        for (int i = 0; i < POPULATION_SIZE; i++) {
            EmploisTp emploisTp = new EmploisTp(ImportData.filieres.size());
            for (int filierIndice = 0; filierIndice < ImportData.filieres.size(); filierIndice++) {
                Filiere filiere = ImportData.filieres.get(filierIndice);
                List<Seance> seances = getSeanceFiliere(filiere);

                Collections.shuffle(seances);

                for (Seance seance : seances) {
                    DayOfWeek day = getRandomDay();
                    Periode periode = getRandomPeriode(day);
                    int salleIndice = getRandomSalle(seance);
                    seance.setJour(day);
                    seance.setPeriode(periode);
                    seance.setSalle(ImportData.salles.get(salleIndice));
                    emploisTp.addSeance(filierIndice, seance);
                }
            }
            population.add(emploisTp);
        }
    }



    public void evaluate() {
        for (int generation = 0; generation < MAX_GENERATIONS; generation++) {
            List<EmploisTp> newPopulation = new ArrayList<>(POPULATION_SIZE);

            for (int j = 0; j < POPULATION_SIZE / 2; j++) {
                EmploisTp parent1 = selectParent();
                EmploisTp parent2 = selectParent();

                if (random.nextDouble() <= CROSSOVER_RATE) {
                    List<EmploisTp> children = crossover(parent1, parent2);

                    if (random.nextDouble() <= MUTATION_RATE) {
                        mutate(children.get(0));
                    }
                    if (random.nextDouble() <= MUTATION_RATE) {
                        mutate(children.get(1));
                    }

                    newPopulation.add(children.get(0));
                    newPopulation.add(children.get(1));
                } else {
                    newPopulation.add(parent1);
                    newPopulation.add(parent2);
                }
            }

            for (EmploisTp emploisTp : newPopulation) {
                emploisTp.calculateFitness();
            }

            population = newPopulation;

            System.out.println("Generation " + generation + " with population size: " + population.size());


            // Termination condition based on fitness threshold or lack of improvement
            if (isTerminationConditionMet() && isTerminationConditionMet(generation,MAX_GENERATIONS)) {
                break;
            }
        }
    }

    public EmploisTp getBestTimetable() {


        EmploisTp bestSchoolTimetable = population.get(0);
        double bestFitness = bestSchoolTimetable.getFitness();

        for (int i = 1; i < population.size(); i++) {
            EmploisTp currentSchoolTimetable = population.get(i);
            double currentFitness = currentSchoolTimetable.getFitness();

            if (currentFitness < bestFitness) {
                bestSchoolTimetable = currentSchoolTimetable;
                bestFitness = currentFitness;
            }

        }

        return bestSchoolTimetable;
    }

    private boolean isTerminationConditionMet() {
        return getBestTimetable().getFitness() == 0.1 ;
    }

    private boolean isTerminationConditionMet(int generation , int MAX_GENERATIONS) {
        return generation>MAX_GENERATIONS ;
    }


    private EmploisTp selectParent() {
        int totalFitness = 0;
        for (EmploisTp schoolTimetable : population) {
            totalFitness += schoolTimetable.calculateFitness();
        }

        if (totalFitness > 0) {
            int randomFitness = random.nextInt(totalFitness);
            int cumulativeFitness = 0;

            for (EmploisTp emploisTp : population) {
                cumulativeFitness += (totalFitness - emploisTp.calculateFitness());
                if (cumulativeFitness > randomFitness) {
                    return emploisTp;
                }
            }
        }

        return population.get(random.nextInt(population.size()));
    }


    public List<EmploisTp> crossover(EmploisTp parent1, EmploisTp parent2) {
        Random random = new Random();
        int nbrFiliere = parent1.getNbrFilieres();
        EmploisTp offspring1 = new EmploisTp(nbrFiliere);
        EmploisTp offspring2 = new EmploisTp(nbrFiliere);

        // Perform crossover for each class
        for (int filierIndice = 0; filierIndice < nbrFiliere; filierIndice++) {
            List<Seance> parent1EmploiTp = parent1.getEmploiTp(filierIndice);
            List<Seance> parent2EmploiTp = parent2.getEmploiTp(filierIndice);

            int timetableSize = parent1EmploiTp.size();
            int upperBound = (timetableSize / 2) - 1;

            if (upperBound > 0) {
                int crossoverPoint = random.nextInt(upperBound) + 1;

                List<Seance> child1EmploiTp = new ArrayList<>(parent1EmploiTp.subList(0, crossoverPoint));
                List<Seance> child2Timetable = new ArrayList<>(parent2EmploiTp.subList(0, crossoverPoint));

                List<Seance> remainingElementsParent1 = parent1EmploiTp.stream()
                        .filter(element -> !child2Timetable.contains(element))
                        .toList();

                List<Seance> remainingElementsParent2 = parent2EmploiTp.stream()
                        .filter(element -> !child1EmploiTp.contains(element))
                        .toList();

                child1EmploiTp.addAll(remainingElementsParent2);
                child2Timetable.addAll(remainingElementsParent1);


                offspring1.getEmploiTp(filierIndice).addAll(child1EmploiTp);
                offspring2.getEmploiTp(filierIndice).addAll(child2Timetable);
            } else {

                offspring1.getEmploiTp(filierIndice).addAll(parent1EmploiTp);
                offspring2.getEmploiTp(filierIndice).addAll(parent2EmploiTp);
            }
        }

        List<EmploisTp> offspring = new ArrayList<>();
        offspring.add(offspring1);
        offspring.add(offspring2);

        return offspring;
    }


    private void mutate(EmploisTp emploisTp) {

        int nbrFilieres = emploisTp.getNbrFilieres();
        if (nbrFilieres <= 0) {
            throw new IllegalArgumentException("Number of filieres must be positive");
        }

        int filiereIndice = random.nextInt(nbrFilieres);
        List<Seance> filiereEmploi = emploisTp.getEmploiTp(filiereIndice);


        int nbrSeances = filiereEmploi.size();
        if (nbrSeances <= 1) {
            throw new IllegalArgumentException("Number of seances in filiere must be greater than 1 to perform mutation");
        }

        int position1 = random.nextInt(nbrSeances);
        int position2 = random.nextInt(nbrSeances);
        while (position1 == position2) {
            position2 = random.nextInt(nbrSeances);
        }

        DayOfWeek randomDay1 = getRandomDay();
        Periode randomPeriod1 = getRandomPeriode(randomDay1);
        filiereEmploi.get(position1).setJour(randomDay1);
        filiereEmploi.get(position1).setPeriode(randomPeriod1);
        DayOfWeek randomDay2 = getRandomDay();
        Periode randomPeriod2 = getRandomPeriode(randomDay2);
        filiereEmploi.get(position2).setJour(randomDay2);
        filiereEmploi.get(position2).setPeriode(randomPeriod2);
        emploisTp.mutation(filiereIndice, position1, position2);
    }

}