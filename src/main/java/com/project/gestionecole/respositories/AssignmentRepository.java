package com.project.gestionecole.respositories;

import com.project.gestionecole.models.AssignedModule;
import com.project.gestionecole.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssignmentRepository extends JpaRepository <AssignedModule,Integer>{
//    List<AssignedModule> findByModuleId(int moduleId);
//    @Query("SELECT e FROM AssignedModule e WHERE e.student.id = :studentId AND e.module.id = :moduleId")
//    Enrollment findByStudentIdAndModuleId(@Param("studentId") int studentId, @Param("moduleId") int moduleId);
//    @Modifying
//    @Query("DELETE FROM Enrollment e WHERE e.module.id = :moduleId")
//    void deleteByModuleId(@Param("moduleId") int moduleId);
//    @Modifying
//    @Query("DELETE FROM Enrollment e WHERE e.student.id = :studentId")
//    void deleteByStudentId(@Param("studentId") int studentId);
}
