package ma.pfe.backend.service;

import lombok.AllArgsConstructor;
import ma.pfe.backend.AlgGen.Algo;
import ma.pfe.backend.AlgGen.EmploisTp;
import ma.pfe.backend.donnee.ImportData;
import ma.pfe.backend.entites.Filiere;
import ma.pfe.backend.entites.Seance;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class EmploiService implements IEmploiService{
    private final ImportData importData;
    private final ISeanceService iSeanceService;


    @Override
    public List<Map<Long, List<Seance>>> getEmplois() {
        List<Map<Long, List<Seance>>> emplois = new ArrayList<>();
        importData.importData();
        List<Filiere> filieres = importData.filieres;
        for (Filiere filiere : filieres){
            Map<Long, List<Seance>> emploi = new HashMap<>();
            emploi.put(filiere.getID(), iSeanceService.getEmploisParFiliere(filiere.getNom()));
            emplois.add(emploi);
        }
        return emplois;
    }

    @Override
    public List<Seance> getEmploiParFiliere(String nomFiliere) {
        return iSeanceService.getEmploisParFiliere(nomFiliere);
    }

    @Override
    public List<Map<Long, List<Seance>>> gernererEmplois() {
        List<Map<Long, List<Seance>>> emplois = new ArrayList<>();
        importData.importData();
        Algo algorithm = new Algo();
        algorithm.initializePopulation();
        algorithm.evaluate();
        EmploisTp emploisTp = algorithm.getBestTimetable();

        for (int i = 0; i < emploisTp.getNbrFilieres(); i++) {
            Map<Long, List<Seance>> emploi = new HashMap<>();
            emploi.put(emploisTp.getFilieres().get(i).getID(), emploisTp.getEmploiTp(i));
            emplois.add(emploi);
        }

        for (Seance seance : emploisTp.getSeances()) {
            iSeanceService.addSeance(seance);
        }

        return emplois;
    }
}
