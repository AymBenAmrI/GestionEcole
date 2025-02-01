package com.project.gestionecole.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
public class AssignedModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "teacherId", referencedColumnName = "id")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "moduleId", referencedColumnName = "id")
    private Module module;

    private int academicYear;

    private int totalHours;
    private Date createdAt;

    private Date updatedAt;

    public AssignedModule() {
    }

    public AssignedModule(int id, Professor professor, Module module, int academicYear, int totalHours, Date createdAt, Date updatedAt) {
        this.id = id;
        this.professor = professor;
        this.module = module;
        this.academicYear = academicYear;
        this.totalHours = totalHours;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
