package com.project.gestionecole.respositories;

import com.project.gestionecole.models.Departement;
import com.project.gestionecole.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long> {
    public Departement findByName(String name);

    @Query("SELECT d.professors from Departement d where d.id = :id ")
    public List<Professor> getProfessorsOfDeprtement(@Param("id") Long id);
}
