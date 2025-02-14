package ma.pfe.backend.repository;

import ma.pfe.backend.entites.Filiere;
import ma.pfe.backend.entites.Seance;
import ma.pfe.backend.enumeration.Periode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;

public interface SeanceRepos extends JpaRepository<Seance,Long> {

    @Query("select s from Seance s where s.filiere.nom = ?1")
    List<Seance> getEmploiByFiliere(@Param("filiereNom")String filiereNom);

    @Query("SELECT s FROM Seance s WHERE s.jour = :dayOfWeek AND s.periode = :periode AND s.filiere.ID = :filiereID")
    List<Seance> getSeanceByJourAndPeriodeAndFiliere(@Param("dayOfWeek")DayOfWeek dayOfWeek, @Param("periode")Periode periode, @Param("filiereID") Long filiereID);

    @Transactional
    @Modifying
    @Query("DELETE FROM Seance f WHERE f.nomSeance = :nomSeance")
    void deleteSeanceBynom(@Param("nomSeance") String nomSeance);

    @Query("SELECT f FROM Seance f ORDER BY f.id")
    List<Seance> findAllByOrderById();


    @Transactional
    @Modifying
    @Query("UPDATE Seance f SET f.id = :newId WHERE f.id = :currentId")
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
