package com.project.gestionecole.respositories;


import com.project.gestionecole.models.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {
    @Query("SELECT m FROM Module m ORDER BY SIZE(m.enrollments) DESC")
    List<Module> findMostPopularModules();

}
