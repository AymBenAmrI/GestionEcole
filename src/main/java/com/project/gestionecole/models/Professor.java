package com.project.gestionecole.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Professor extends User{
    private String matricule;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hiringDate;

    @OneToMany(mappedBy = "professor")
    private List<AssignedModule> assignedModules;

    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;

    @ManyToMany
    @JoinTable(
            name = "professor_module",
            joinColumns = @JoinColumn(name = "professor_id"),
            inverseJoinColumns = @JoinColumn(name = "module_id")
    )
    private List<Module> modules = new ArrayList<>();
    public Professor(int id, String firstName, String lastName, String email, String password, boolean active, String address, LocalDate birthDate, Date createdAt, Date updatedAt, List<Role> roles) {
        super(id, firstName, lastName, email, password, active, address, birthDate, createdAt, updatedAt, roles);
    }

    public Professor(int id, String firstName, String lastName, String email, String password, boolean active, String address, LocalDate birthDate, Date createdAt, Date updatedAt, List<Role> roles, String matricule, LocalDate
            hiringDate, List<AssignedModule> assignedModules, Departement departement) {
        super(id, firstName, lastName, email, password, active, address, birthDate, createdAt, updatedAt, roles);
        this.matricule = matricule;
        this.departement = departement;
        this.hiringDate = hiringDate;
        this.assignedModules = assignedModules;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Professor() {
    }



    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public LocalDate getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(LocalDate hiringDate) {
        this.hiringDate = hiringDate;
    }

    public List<AssignedModule> getAssignedModules() {
        return assignedModules;
    }

    public void setAssignedModules(List<AssignedModule> assignedModules) {
        this.assignedModules = assignedModules;
    }

    public List<Module> getModules() {
        return modules;
    }
}
