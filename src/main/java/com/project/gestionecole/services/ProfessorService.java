package com.project.gestionecole.services;

import com.project.gestionecole.models.*;
import com.project.gestionecole.models.Module;
import com.project.gestionecole.respositories.DepartementRepository;
import com.project.gestionecole.respositories.ModuleRepository;
import com.project.gestionecole.respositories.ProfessorRepository;
import com.project.gestionecole.respositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private DepartementRepository departementRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfessorService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public List<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }

    public Professor getProfessorById(int id) {
        return professorRepository.findById(id).orElse(null);
    }

    public Professor saveProfessor(Professor professor) {
        Role existingRole = roleRepository.findByName(RoleNames.PROFESSOR);
        if (existingRole == null) {
            throw new RuntimeException("Role not found: " + RoleNames.PROFESSOR);
        }
        professor.setPassword(passwordEncoder.encode("password"));  // Set the new password
        professor.setActive(true);
        professor.setRoles(List.of(existingRole));

        return professorRepository.save(professor);
    }

    public void deleteProfessor(int id) {
        professorRepository.deleteById(id);
    }

    public void assignModules(Professor professor, List<AssignedModule> modules) {
        professor.setAssignedModules(modules);
        professorRepository.save(professor);
    }
    public Professor updateProfessor(Professor updatedProfessor) {
        Optional<Professor> existingProfessor = professorRepository.findById(updatedProfessor.getId());
        if (existingProfessor.isPresent()) {
            Professor professor = existingProfessor.get();

            // Update fields
            professor.setFirstName(updatedProfessor.getFirstName());
            professor.setLastName(updatedProfessor.getLastName());
            professor.setEmail(updatedProfessor.getEmail());
            professor.setPassword(updatedProfessor.getPassword());
            professor.setAddress(updatedProfessor.getAddress());
            professor.setActive(updatedProfessor.isActive());
            professor.setBirthDate(updatedProfessor.getBirthDate());
            professor.setDepartement(updatedProfessor.getDepartement());
            professor.setHiringDate(updatedProfessor.getHiringDate());
            professor.setAssignedModules(updatedProfessor.getAssignedModules());

            // Save the updated professor
            return professorRepository.save(professor);
        } else {
            return null; // Or throw an exception if preferred
        }
    }

    public Departement getDertement(String name) {
        return departementRepository.findByName(name);
    }

    public void assignModuleToProfessor(int professorId, int moduleId) {
        Professor professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor not found"));
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found"));

        if(professor.getModules().contains(module)){
            return;
        }

        professor.getModules().add(module);
        professorRepository.save(professor);
    }

    public List<Professor> findAllWithModules(){
        return professorRepository.findAllWithModules();
    }
}

