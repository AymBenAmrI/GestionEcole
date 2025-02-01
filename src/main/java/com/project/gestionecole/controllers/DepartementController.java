package com.project.gestionecole.controllers;

import com.project.gestionecole.services.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Controller
@RequestMapping("/departements")
public class DepartementController {
    @Autowired
    private DepartementService departementService;

    @GetMapping
    public String listDepartements(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList(); // Convert authorities to a list of role names

        model.addAttribute("departements", departementService.getAllDertement());
        model.addAttribute("userRole", roles);
        return "departement/list";
    }
}
