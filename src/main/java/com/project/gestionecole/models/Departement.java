package com.project.gestionecole.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String location;

    @OneToMany(mappedBy = "departement")
    private List<Professor> professors;

    public String getName(){
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public List<Professor> getProfessors() {
        return professors;
    }
}
