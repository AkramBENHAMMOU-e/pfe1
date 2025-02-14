package ma.pfe.backend.repository;

import ma.pfe.backend.entites.Salle;
import ma.pfe.backend.entites.Seance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SalleRepos extends JpaRepository<Salle,Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Salle f WHERE f.nomSalle = :nomSalle")
    void deleteSalleByNom(@Param("nomSalle") String nomSalle);

    @Query("SELECT f FROM Salle f ORDER BY f.id")
    List<Salle> findAllByOrderById();


    @Transactional
    @Modifying
    @Query("UPDATE Salle f SET f.id = :newId WHERE f.id = :currentId")
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
