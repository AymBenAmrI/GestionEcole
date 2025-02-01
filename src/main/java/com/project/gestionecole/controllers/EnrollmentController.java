package com.project.gestionecole.controllers;

import com.project.gestionecole.dtos.CancelEnrollmentRequest;
import com.project.gestionecole.dtos.EnrollStudentRequest;
import com.project.gestionecole.models.Module;
import com.project.gestionecole.models.Enrollment;
import com.project.gestionecole.models.Student;
import com.project.gestionecole.services.EnrollmentService;
import com.project.gestionecole.services.ModuleService;
import com.project.gestionecole.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private StudentService studentService;

    // Enroll a student in a module
    @GetMapping("/enroll")
    public String showEnrollForm(Model model) {
        List<Module> modules = moduleService.getAllModules();
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("modules", modules);
        model.addAttribute("students", students);
        model.addAttribute("enrollRequest", new EnrollStudentRequest());
        return "enroll-student";
    }
    @PostMapping("/enroll-batch")
    public String enrollStudentsBatch(@RequestParam List<Integer> studentIds, @RequestParam int moduleId, Model model) {
        System.out.println(studentIds);
        System.out.println(moduleId);
        String response = enrollmentService.enrollStudentsInModule(studentIds, moduleId);
        model.addAttribute("response", response);
        return "redirect:/students";
    }

    @PostMapping("/enroll")
    public String enrollStudent(@ModelAttribute EnrollStudentRequest request, Model model) {
        String response = enrollmentService.enrollStudent(request);
        model.addAttribute("response", response);
        return "redirect:/inscriptions/enroll";
    }

    // Cancel an enrollment
//    @GetMapping("/cancel")
//    public String showCancelEnrollmentForm(Model model) {
//        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByModule(1); // Example, you can modify this
//        model.addAttribute("enrollments", enrollments);
//        return "cancel-enrollment";
//    }
    @PostMapping( "/remove")
    public String removeEnrollment(@RequestParam int studentId, @RequestParam int moduleId, RedirectAttributes redirectAttributes) {
        String result = enrollmentService.removeEnrollmentFromModule(studentId, moduleId);
        redirectAttributes.addFlashAttribute("message", result);
        return "redirect:/students/" + studentId;
    }

//    @PostMapping("/cancel")
//    public String cancelEnrollment(@ModelAttribute CancelEnrollmentRequest request, Model model) {
//        String response = enrollmentService.cancelEnrollment(request);
//        model.addAttribute("response", response);
//        return "redirect:/inscriptions/cancel";
//    }

    // View enrollments by module
    @GetMapping("/view/{moduleId}")
    public String viewEnrollmentsByModule(@PathVariable int moduleId, Model model) {
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByModule(moduleId);
        model.addAttribute("enrollments", enrollments);
        model.addAttribute("moduleId", moduleId);
        return "view-enrollments";
    }
}
