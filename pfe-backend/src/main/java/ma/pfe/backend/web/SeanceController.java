package ma.pfe.backend.web;

import lombok.AllArgsConstructor;
import ma.pfe.backend.entites.Salle;
import ma.pfe.backend.entites.Seance;
import ma.pfe.backend.service.ISeanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/Seance")
public class SeanceController {
    private final ISeanceService iSeanceService;
    @GetMapping("/{id}")
    Seance getSeanceByID(@PathVariable Long id){
        return iSeanceService.getSeanceById(id);
    }
    @GetMapping
    List<Seance> getAllSeances() {
        return iSeanceService.getSeances();
    }

    @PostMapping
    public Seance addSeance(@RequestBody Seance seance) {
        System.out.println("Received Seance: " + seance);
        return iSeanceService.addSeance(seance);
    }
    @DeleteMapping("/{nom}")
    public void deleteSeance(@PathVariable String nom){iSeanceService.deleteSeance(nom);}

    @PutMapping("/{id}")
    public Seance modifierSeance(@PathVariable Long id, @RequestBody Seance modifSeace) {
        return iSeanceService.modifierSeance(id, modifSeace);
    }
}
