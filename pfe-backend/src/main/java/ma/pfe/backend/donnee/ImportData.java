package ma.pfe.backend.donnee;

import lombok.AllArgsConstructor;
import ma.pfe.backend.entites.Filiere;
import ma.pfe.backend.entites.Salle;
import ma.pfe.backend.entites.Seance;
import ma.pfe.backend.service.IFilierService;
import ma.pfe.backend.service.ISalleService;
import ma.pfe.backend.service.ISeanceService;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@AllArgsConstructor
public class ImportData {
   public static List<Salle> salles;
   static List<Seance> seances;
   public static List<Filiere> filieres;


   private  ISalleService iSalleService;
   private  ISeanceService iSeanceService;
   private  IFilierService iFilierService;

    public void importData(){
       salles = iSalleService.getSalles();
       seances = iSeanceService.getSeances();
       filieres = iFilierService.getFilieres();

   }
}
