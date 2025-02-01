package com.project.gestionecole.controllers;
import com.project.gestionecole.dtos.AddProfessorRequest;
import com.project.gestionecole.dtos.EditProfessorRequest;
import com.project.gestionecole.models.Professor;
import com.project.gestionecole.models.Role;
import com.project.gestionecole.models.RoleNames;
import com.project.gestionecole.services.DepartementService;
import com.project.gestionecole.services.ModuleService;
import com.project.gestionecole.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Controller
@RequestMapping("/professors")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;

    @Autowired
    private DepartementService departementService;

    @Autowired
    private ModuleService moduleService;

    @GetMapping
    public String listProfessors(Model model) {
        addAuthenticationDetailsToModel(model);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList(); // Convert authorities to a list of role names

        model.addAttribute("professors", professorService.findAllWithModules());
        model.addAttribute("userRole", roles);
        model.addAttribute("modules",moduleService.getAllModules());
        return "professors/list";
    }
    private void addAuthenticationDetailsToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the username/email of the logged-in user
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList(); // Convert authorities to a list of role names

        model.addAttribute("userRole", roles);
        model.addAttribute("username", username);
    }


    @GetMapping("/add")
    public String showAddForm(Model model) {
        addAuthenticationDetailsToModel(model);

        model.addAttribute("addProfessorRequest", new AddProfessorRequest());
        model.addAttribute("departements", departementService.getAllDertement());
        return "professors/add";
    }

    @PostMapping("/add")
    public String addProfessor(@ModelAttribute AddProfessorRequest addProfessorRequest,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "students/add";
        }
        Professor professor = new Professor();
        Role role = new Role();
        role.setName(RoleNames.PROFESSOR);
        professor.setFirstName(addProfessorRequest.getFirstName());
        professor.setLastName(addProfessorRequest.getLastName());
        professor.setEmail(addProfessorRequest.getEmail());
        professor.setPassword(addProfessorRequest.getPassword());
        professor.setAddress(addProfessorRequest.getAddress());
        professor.setActive(addProfessorRequest.isActive());
        professor.setBirthDate(addProfessorRequest.getBirthDate());
        professor.setDepartement(professorService.getDertement(addProfessorRequest.getDepartement()));
        professor.setHiringDate(addProfessorRequest.getHiringDate());
        professor.setRoles(List.of(role));

        professorService.saveProfessor(professor);
        return "redirect:/professors";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        addAuthenticationDetailsToModel(model);

        Professor professor = professorService.getProfessorById(id);

        if (professor == null) {
            return "redirect:/professors";
        }
        EditProfessorRequest editProfessorRequest = new EditProfessorRequest();
        editProfessorRequest.setId(professor.getId());
        editProfessorRequest.setFirstName(professor.getFirstName());
        editProfessorRequest.setLastName(professor.getLastName());
        editProfessorRequest.setEmail(professor.getEmail());
        editProfessorRequest.setPassword(professor.getPassword());
        editProfessorRequest.setAddress(professor.getAddress());
        editProfessorRequest.setActive(professor.isActive());
        editProfessorRequest.setBirthDate(professor.getBirthDate());
        editProfessorRequest.setHiringDate(professor.getHiringDate());
        if (professor.getDepartement() != null) {
            editProfessorRequest.setDepartement(professor.getDepartement().getName());
        } else {
            // Handle the case where departement is null, e.g., set a default value or leave it blank
            editProfessorRequest.setDepartement("No Department");
        }

        model.addAttribute("departements", departementService.getAllDertement());
        model.addAttribute("editProfessorRequest", editProfessorRequest);
        return "professors/edit";
    }

    @PostMapping("/edit/{id}")
    public String editProfessor(@PathVariable int id,@ModelAttribute EditProfessorRequest editProfessorRequest) {
        Professor professor = professorService.getProfessorById(id);
        if (professor == null) {
            return "redirect:/professors";
        }
        professor.setId(id);
        professor.setFirstName(editProfessorRequest.getFirstName());
        professor.setLastName(editProfessorRequest.getLastName());
        professor.setEmail(editProfessorRequest.getEmail());
        professor.setPassword(editProfessorRequest.getPassword());
        professor.setAddress(editProfessorRequest.getAddress());
        professor.setActive(editProfessorRequest.isActive());
        professor.setBirthDate(editProfessorRequest.getBirthDate());
        professor.setDepartement(professorService.getDertement(editProfessorRequest.getDepartement()));
        professor.setHiringDate(editProfessorRequest.getHiringDate());

        professorService.updateProfessor(professor);
        return "redirect:/professors";
    }

    @GetMapping("/delete/{id}")
    public String deleteProfessor(@PathVariable int id) {
        professorService.deleteProfessor(id);
        return "redirect:/professors";
    }

    @GetMapping("/details/{id}")
    public String showProfessorDetails(@PathVariable int id, Model model) {
        addAuthenticationDetailsToModel(model);

        Professor professor = professorService.getProfessorById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList(); // Convert authorities to a list of role names
        model.addAttribute("professor", professor);
        model.addAttribute("userRole", roles);
        model.addAttribute("assignedModules", professor.getAssignedModules());
        model.addAttribute("departments", departementService.getAllDertement());
        return "professors/details";
    }

    @PostMapping("/assignModule")
    public String assignModule(@RequestParam int professorId, @RequestParam int moduleId) {
        professorService.assignModuleToProfessor(professorId, moduleId);
        return "redirect:/professors";
    }
}

