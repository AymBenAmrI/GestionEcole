package com.project.gestionecole.services;

import com.project.gestionecole.models.Departement;
import com.project.gestionecole.models.Professor;
import com.project.gestionecole.respositories.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementService {
    @Autowired
    private DepartementRepository departementRepository;

    public List<Departement> getAllDertement(){
        return departementRepository.findAll();
    }

    public List<Professor> getProfessorsOfDepartement(Long id) {
        return departementRepository.getProfessorsOfDeprtement(id);
    }
}
