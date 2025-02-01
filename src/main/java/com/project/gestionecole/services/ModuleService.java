package com.project.gestionecole.services;

import com.project.gestionecole.models.Module;
import com.project.gestionecole.respositories.EnrollmentRepository;
import com.project.gestionecole.respositories.ModuleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleService {
    @Autowired
    private final ModuleRepository moduleRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    // Create or update a module
    public Module saveModule(Module module) {
        return moduleRepository.save(module);
    }

    // Retrieve all modules
    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    // Retrieve a module by ID
    public Optional<Module> getModuleById(int id) {
        return moduleRepository.findById(id);
    }

    // Delete a module by ID
    @Transactional
    public void deleteModule(int id) {
        // Delete all enrollments related to the module
        enrollmentRepository.deleteByModuleId(id);

        // Now delete the module
        moduleRepository.deleteById(id);
    }

    public long countModules() {
        return moduleRepository.count();
    }

    public List<Module> getMostPopularModules() {
        // Assuming there's a method in the repository to fetch popular modules
        return moduleRepository.findMostPopularModules();
    }
}
