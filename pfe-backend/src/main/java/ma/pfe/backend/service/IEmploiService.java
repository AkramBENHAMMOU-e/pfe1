package ma.pfe.backend.service;

import ma.pfe.backend.entites.Seance;

import java.util.List;
import java.util.Map;

public interface IEmploiService {
List<Map<Long, List<Seance>>> getEmplois();
List<Seance> getEmploiParFiliere(String nomFiliere);
List<Map<Long, List<Seance>>> gernererEmplois();
}
