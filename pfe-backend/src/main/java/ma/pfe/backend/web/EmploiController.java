package ma.pfe.backend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.pfe.backend.entites.Seance;
import ma.pfe.backend.service.IEmploiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/Emploi")
@CrossOrigin("*")
@AllArgsConstructor
public class EmploiController {
    private IEmploiService iEmploiService;
    @GetMapping
    List<Map<Long , List<Seance>>> getEmplois(){
        return iEmploiService.getEmplois();
    }
    @GetMapping("/{nomFiliere}")
    List<Seance> getEmployParFiliere(@PathVariable String nomFiliere){
        return iEmploiService.getEmploiParFiliere(nomFiliere);
    }
    @GetMapping("/generer")
    List<Map<Long, List<Seance>>> genereEmploi(){
        return iEmploiService.gernererEmplois();
    }

}
