package com.project.gestionecole.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
public class Student extends User{
    private String matricule;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments = new ArrayList<>();


    public Student(int id, String firstName, String lastName, String email, String password, boolean active, String address, LocalDate birthDate, Date createdAt, Date updatedAt, List<Role> roles) {
        super(id, firstName, lastName, email, password, active, address, birthDate, createdAt, updatedAt, roles);
    }

    public Student() {
    }

    public Student(int id, String firstName, String lastName, String email, String password, boolean active, String address, LocalDate birthDate, Date createdAt, Date updatedAt, List<Role> roles, String matricule, List<Enrollment> enrollments) {
        super(id, firstName, lastName, email, password, active, address, birthDate, createdAt, updatedAt, roles);
        this.matricule = matricule;
        this.enrollments = enrollments;
    }

    public Student(String matricule, List<Enrollment> enrollments) {
        this.matricule = matricule;
        this.enrollments = enrollments;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    @Override
    public String toString() {
        return "Student{" +
                "matricule='" + matricule + '\'' +
                ", enrollments=" + enrollments +
                '}';
    }
}
