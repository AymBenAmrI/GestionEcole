package com.project.gestionecole.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String code;

    private String name;

    private String description;

    private int totalHours;

    @OneToMany(mappedBy = "module")
    private List<AssignedModule> assignedModules;

    @OneToMany(mappedBy = "module")
    private List<Enrollment> enrollments;

    @ManyToMany(mappedBy = "modules")
    private List<Professor> professors = new ArrayList<>();
    private Date createdAt;

    private Date updatedAt;

    public Module() {
    }

    public Module(int id, String code, String name, String description, int totalHours, List<AssignedModule> assignedModules, List<Enrollment> enrollments, Date createdAt, Date updatedAt) {

        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.totalHours = totalHours;
        this.assignedModules = assignedModules;
        this.enrollments = enrollments;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public List<AssignedModule> getAssignedModules() {
        return assignedModules;
    }

    public void setAssignedModules(List<AssignedModule> assignedModules) {
        this.assignedModules = assignedModules;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
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
