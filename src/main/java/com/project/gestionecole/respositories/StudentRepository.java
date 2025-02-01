package com.project.gestionecole.respositories;

import com.project.gestionecole.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
}

