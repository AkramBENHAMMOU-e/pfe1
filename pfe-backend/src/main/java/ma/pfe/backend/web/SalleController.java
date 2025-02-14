package ma.pfe.backend.web;

import lombok.AllArgsConstructor;
import ma.pfe.backend.entites.Filiere;
import ma.pfe.backend.entites.Salle;
import ma.pfe.backend.service.ISalleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/Salle")
public class SalleController {
    private ISalleService iSalleService;
    @GetMapping("/{id}")
    Salle getSalleByID(@PathVariable Long id){
        return iSalleService.getSalleById(id);
    }
    @GetMapping
    List<Salle> getAllSalles(){
        return iSalleService.getSalles();
    }
    @PostMapping
    Salle addSalle(@RequestBody Salle salle){
        return iSalleService.addSalle(salle);
    }
    @DeleteMapping("/{nom}")
    void deleteSalle(@PathVariable String nom){iSalleService.deleteSalleByNom(nom);}

    @PutMapping("/{id}")
    public Salle modifierFiliere(@PathVariable Long id, @RequestBody Salle modifSalle) {
        return iSalleService.modifierSalle(id, modifSalle);
    }
}
