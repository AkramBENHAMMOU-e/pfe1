package ma.pfe.backend.repository;

import ma.pfe.backend.entites.Filiere;
import ma.pfe.backend.entites.Seance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
public interface FiliereRepos extends JpaRepository<Filiere, Long> {

    @Query("select s from Filiere s where s.nom = ?1")
    Filiere getFiliereByName(@Param("filiereNom") String filiereNom);

    @Query("SELECT f FROM Filiere f ORDER BY f.ID")
    List<Filiere> findAllByOrderById();

    @Transactional
    @Modifying
    @Query("DELETE FROM Filiere f WHERE f.nom = :filiereNom")
    void deleteFiliereByNom(@Param("filiereNom") String filiereNom);

    @Transactional
    @Modifying
    @Query("UPDATE Filiere f SET f.ID = :newId WHERE f.ID = :currentId")
    void updateFiliereId(@Param("currentId") Long currentId, @Param("newId") Long newId);

    @Transactional
    @Modifying
    @Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true)
    void disableForeignKeyChecks();

    @Transactional
    @Modifying
    @Query(value = "SET FOREIGN_KEY_CHECKS = 1", nativeQuery = true)
    void enableForeignKeyChecks();
}
