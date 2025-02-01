package com.project.gestionecole.controllers;


import com.project.gestionecole.dtos.AddStudentRequest;
import com.project.gestionecole.dtos.EditStudentRequest;
import com.project.gestionecole.models.Role;
import com.project.gestionecole.models.RoleNames;
import com.project.gestionecole.models.Student;
import com.project.gestionecole.services.ModuleService;
import com.project.gestionecole.services.StudentService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    @Autowired
    private ModuleService moduleService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
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


    @GetMapping
    public String listStudents(Model model) {
        addAuthenticationDetailsToModel(model);

        List<Student> students = studentService.getAllStudents();

        model.addAttribute("modules", moduleService.getAllModules());
        model.addAttribute("students", students);

        return "students/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        addAuthenticationDetailsToModel(model);
        model.addAttribute("addStudentRequest", new AddStudentRequest());
        return "students/add";
    }

    @PostMapping("/add")
    public String addStudent( @ModelAttribute("addStudentRequest") AddStudentRequest addStudentRequest,
                              BindingResult result,Model model) {

        if (result.hasErrors()) {
            return "students/add";
        }
        addAuthenticationDetailsToModel(model);

        Student student = new Student();
        Role role = new Role();
        role.setName(RoleNames.STUDENT);
        student.setMatricule(addStudentRequest.getMatricule());
        student.setFirstName(addStudentRequest.getFirstName());
        student.setLastName(addStudentRequest.getLastName());
        student.setEmail(addStudentRequest.getEmail());
        student.setAddress(addStudentRequest.getAddress());
        student.setBirthDate(addStudentRequest.getBirthDate());
        student.setRoles(List.of(role));
        studentService.addStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        addAuthenticationDetailsToModel(model);

        Student student = studentService.getStudentById(id);

        EditStudentRequest editStudentRequest = new EditStudentRequest();
        editStudentRequest.setId(id);
        editStudentRequest.setMatricule(student.getMatricule());
        editStudentRequest.setFirstName(student.getFirstName());
        editStudentRequest.setLastName(student.getLastName());
        editStudentRequest.setEmail(student.getEmail());
        editStudentRequest.setAddress(student.getAddress());
        editStudentRequest.setBirthDate(student.getBirthDate());

        model.addAttribute("editStudentRequest", editStudentRequest);
        return "students/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable int id, @ModelAttribute("editStudentRequest") EditStudentRequest editStudentRequest,
                                BindingResult result,Model model) {
        addAuthenticationDetailsToModel(model);
        if (result.hasErrors()) {
            return "students/edit";
        }
        Student student = studentService.getStudentById(id);
        student.setMatricule(editStudentRequest.getMatricule());
        student.setFirstName(editStudentRequest.getFirstName());
        student.setLastName(editStudentRequest.getLastName());
        student.setEmail(editStudentRequest.getEmail());
        student.setAddress(editStudentRequest.getAddress());
        student.setBirthDate(editStudentRequest.getBirthDate());

        studentService.updateStudent(id, student);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable int id) {

        studentService.deleteStudent(id);
        return "redirect:/students";
    }

    @GetMapping("/search")
    public String searchStudents(@RequestParam String keyword, Model model) {
        addAuthenticationDetailsToModel(model);
        List<Student> students = studentService.searchStudents(keyword);
        model.addAttribute("students", students);
        return "students/list";
    }

    @GetMapping("/{id}")
    public String studentDetails(@PathVariable int id, Model model) {
        addAuthenticationDetailsToModel(model);
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return "redirect:/students";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList(); // Convert authorities to a list of role names

        model.addAttribute("student", student);
        model.addAttribute("roles", roles);
        model.addAttribute("enrollments", student.getEnrollments());

        return "students/details";
    }
}

