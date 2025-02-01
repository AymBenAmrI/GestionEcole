package com.project.gestionecole.controllers;

import com.project.gestionecole.dtos.ChangePasswordRequest;
import com.project.gestionecole.dtos.LoginRequest;
import com.project.gestionecole.dtos.RegisterRequest;
import com.project.gestionecole.models.*;
import com.project.gestionecole.models.Module;
import com.project.gestionecole.services.AuthService;
import com.project.gestionecole.services.EnrollmentService;
import com.project.gestionecole.services.ModuleService;
import com.project.gestionecole.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;


import java.util.List;

@Controller
public class AuthController {
    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private StudentService studentService;

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,Model model) {
        if (error != null && error.equals("true")) {
            model.addAttribute("error", "Invalid login credentials.");
        }
        model.addAttribute("loginRequest", new LoginRequest());
        return "auth/login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest,Model model) {
        String response = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        model.addAttribute("response", response);
        return "redirect:/home";
    }
    @GetMapping("/home")
    public String home(Model model) {
        long totalStudents = studentService.countStudents();
        long totalModules = moduleService.countModules();
        long totalEnrollments = enrollmentService.countEnrollments();
        List<Module> popularModules = moduleService.getMostPopularModules();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the username/email of the logged-in user
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList(); // Convert authorities to a list of role names
        // Add data to the model
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("totalModules", totalModules);
        model.addAttribute("totalEnrollments", totalEnrollments);
        model.addAttribute("popularModules", popularModules);
        model.addAttribute("userRole", roles);
        model.addAttribute("username", username);

        if(roles.contains("STUDENT")){
            int id = authService.getUserByEmail(username).getId();
            return  "redirect:/students/"+id;
        }

        if(roles.contains("PROFESSOR")){
            int id = authService.getUserByEmail(username).getId();
            return  "redirect:/professors/details/"+id;
        }

        return "home";
    }

    @GetMapping("/register")
    public String register(@RequestParam(value = "error", required = false) String error,Model model) {
        if (error != null && error.equals("true")) {
            model.addAttribute("error", "Error : user already exists.");
        }
        model.addAttribute("registerRequest", new RegisterRequest());
        return "auth/register";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute("registerRequest") RegisterRequest registerRequest, Model model) {
        try {
            User user = new User();
            Role role = new Role();
            role.setName(RoleNames.ADMIN);
            user.setFirstName(registerRequest.getFirstName());
            user.setLastName(registerRequest.getLastName());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(registerRequest.getPassword());
            user.setAddress(registerRequest.getAddress());
            user.setBirthDate(registerRequest.getBirthDate());
            user.setRoles(List.of(role));
            // Call the service to register the user
            authService.register(user);
            model.addAttribute("successMessage", "Registration successful! You can now log in.");
            return "redirect:/login"; // Redirect to login page after successful registration
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Registration failed: " + e.getMessage());
            return "auth/register"; // Stay on the register page and show an error message
        }
    }
    @GetMapping("/logout")
    public String logout() {
        // You can add any custom logic here if needed (like invalidating sessions, etc.)
        SecurityContextHolder.clearContext(); // Clear security context for logged-out user
        return "redirect:/login?logout=true"; // Redirect to login page with a logout message
    }
    // Change Password
    @GetMapping("/change-password")
    public String changePasswordPage(Model model) {
        model.addAttribute("changePasswordRequest", new ChangePasswordRequest());
        return "auth/change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute ChangePasswordRequest changePasswordRequest, Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName(); // Get the current logged-in user's username
            authService.changePassword(changePasswordRequest.getCurrentPassword(), changePasswordRequest.getNewPassword(), changePasswordRequest.getConfirmPassword());
            model.addAttribute("successMessage", "Password changed successfully!");
            return "redirect:/home"; // Redirect to home after successful password change
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Password change failed: " + e.getMessage());
            return "auth/change-password"; // Stay on the change password page and show an error message
        }
    }

}

