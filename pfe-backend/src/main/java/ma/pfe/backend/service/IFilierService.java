    package ma.pfe.backend.service;

    import ma.pfe.backend.entites.Filiere;

    import java.util.List;
    import java.util.Optional;

    public interface IFilierService {
        List<Filiere> getFilieres();
        Filiere getFiliereById(Long id);
        Filiere getFiliereBynom(String nom);

        void deleteFiliereByNOm(String nom);
        String deleteFiliereById(Long id);
        Filiere addFiliere(Filiere filiere);

        Filiere modifierFiliere(Long id, Filiere filiere);
        void refreshFiliereIds();

    }
