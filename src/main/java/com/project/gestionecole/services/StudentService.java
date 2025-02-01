package com.project.gestionecole.services;


import com.project.gestionecole.models.Role;
import com.project.gestionecole.models.RoleNames;
import com.project.gestionecole.respositories.EnrollmentRepository;
import com.project.gestionecole.respositories.RoleRepository;
import com.project.gestionecole.respositories.StudentRepository;
import com.project.gestionecole.models.Student;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService{

    private final StudentRepository studentRepository;
    @Autowired
    private RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private EnrollmentRepository enrollmentRepository;


    public StudentService(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Student addStudent(Student student) {
        Role existingRole = roleRepository.findByName(RoleNames.STUDENT);
        if (existingRole == null) {
            throw new RuntimeException("Role not found: " + RoleNames.STUDENT);
        }
        student.setPassword(passwordEncoder.encode("password"));  // Set the new password
        student.setActive(true);
        student.setRoles(List.of(existingRole));
        return studentRepository.save(student);
    }

    public Student updateStudent(int id, Student student) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if (existingStudent.isPresent()) {
            student.setId(id);
            return studentRepository.save(student);
        }
        throw new RuntimeException("Student not found with ID: " + id);
    }

    // Service Method to Delete a Student
    @Transactional
    public void deleteStudent(int studentId) {
        // Delete all enrollments for the student
        enrollmentRepository.deleteByStudentId(studentId);
        // Delete the student
        studentRepository.deleteById(studentId);
    }



    public Student getStudentById(int id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
    }

    public List<Student> searchStudents(String keyword) {
        return studentRepository.findByFirstNameContainingOrLastNameContaining(keyword, keyword);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public long countStudents() {
        return studentRepository.count();
    }

}

