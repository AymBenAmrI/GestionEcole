package com.project.gestionecole.controllers;

import com.project.gestionecole.dtos.AddModuleRequest;
import com.project.gestionecole.dtos.EditModuleRequest;
import com.project.gestionecole.models.Module;
import com.project.gestionecole.services.ModuleService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/modules")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    // Display list of modules
    @GetMapping
    public String listModules(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList(); // Convert authorities to a list of role names
        model.addAttribute("modules", moduleService.getAllModules());
        model.addAttribute("userRole", roles);
        return "modules/list";
    }

    @GetMapping("/create")
    public String createModuleForm(Model model) {
        model.addAttribute("addModuleRequest", new AddModuleRequest());
        return "modules/add";
    }

    // Save a new module
    @PostMapping("/create")
    public String saveModule( @ModelAttribute("addModuleRequest") AddModuleRequest addModuleRequest) {
        Module module = new Module();
        module.setCode(addModuleRequest.getCode());
        module.setName(addModuleRequest.getName());
        module.setDescription(addModuleRequest.getDescription());
        module.setTotalHours(addModuleRequest.getTotalHours());
        moduleService.saveModule(module);
        return "redirect:/modules";
    }

    @GetMapping("/edit/{id}")
    public String editModuleForm(@PathVariable int id, Model model) {
        Optional<Module> module = moduleService.getModuleById(id);
        if (module.isPresent()) {
            Module existingModule = module.get();
            EditModuleRequest editModuleRequest = new EditModuleRequest();
            editModuleRequest.setId(id);
            editModuleRequest.setCode(existingModule.getCode());
            editModuleRequest.setName(existingModule.getName());
            editModuleRequest.setDescription(existingModule.getDescription());
            editModuleRequest.setTotalHours(existingModule.getTotalHours());
            model.addAttribute("editModuleRequest", editModuleRequest);
            return "modules/edit";
        } else {
            return "redirect:/modules";
        }
    }

    // Update an existing module
    @PostMapping("/edit/{id}")
    public String updateModule(@PathVariable int id,  @ModelAttribute("editModuleRequest") EditModuleRequest editModuleRequest) {
        Optional<Module> optionalModule = moduleService.getModuleById(id);
        if (optionalModule.isPresent()) {
            Module module = optionalModule.get();
            module.setId(id);
            module.setCode(editModuleRequest.getCode());
            module.setName(editModuleRequest.getName());
            module.setDescription(editModuleRequest.getDescription());
            module.setTotalHours(editModuleRequest.getTotalHours());
            moduleService.saveModule(module);
        }
        return "redirect:/modules";
    }

    // View students enrolled in a module
    @GetMapping("/{id}/students")
    public String viewEnrolledStudents(@PathVariable int id, Model model) {
        Optional<Module> module = moduleService.getModuleById(id);
        if (module.isPresent()) {
            model.addAttribute("module", module.get());
            model.addAttribute("students", module.get().getEnrollments()); // Assuming enrollments contain student data
            return "modules/details";
        } else {
            return "redirect:/modules";
        }
    }

    // Delete a module
    @GetMapping("/delete/{id}")
    public String deleteModule(@PathVariable int id) {
        moduleService.deleteModule(id);
        return "redirect:/modules";
    }
}
