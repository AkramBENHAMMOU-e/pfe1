package ma.pfe.backend.web;

import lombok.AllArgsConstructor;
import ma.pfe.backend.entites.Filiere;
import ma.pfe.backend.service.FiliereService;
import ma.pfe.backend.service.IFilierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/Filiere")
public class FiliereController {
    private IFilierService iFilierService;
    @GetMapping("/{id}")
    Filiere getFilierById(@PathVariable Long id){
        return iFilierService.getFiliereById(id);
    }
    @GetMapping
    List<Filiere> getAllFiliere(){
        return iFilierService.getFilieres();
    }
    @PostMapping
    Filiere addFiliere(@RequestBody Filiere filiere){
        return iFilierService.addFiliere(filiere);
    }
    @DeleteMapping("/{nom}")
    void deleteFiliere(@PathVariable String nom){
         iFilierService.deleteFiliereByNOm(nom);
    }
    @PutMapping("/{id}")
    public Filiere modifierFiliere(@PathVariable Long id, @RequestBody Filiere modifFil) {
        return iFilierService.modifierFiliere(id, modifFil);
    }
}
