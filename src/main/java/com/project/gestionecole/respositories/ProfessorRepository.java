package com.project.gestionecole.respositories;

import com.project.gestionecole.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    @Query("SELECT p FROM Professor p LEFT JOIN FETCH p.modules")
    List<Professor> findAllWithModules();

    //@Query(value = "select p.* from Professor p join professor_module pm on p.id = pm.professor_id", nativeQuery = true)

}
