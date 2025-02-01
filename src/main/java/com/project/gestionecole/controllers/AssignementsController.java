package com.project.gestionecole.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AssignementsController {
//    @PostMapping("/enroll-batch")
//    public String enrollStudentsBatch(@RequestParam List<Integer> studentIds, @RequestParam int moduleId, Model model) {
//        System.out.println(studentIds);
//        System.out.println(moduleId);
//        String response = enrollmentService.enrollStudentsInModule(studentIds, moduleId);
//        model.addAttribute("response", response);
//        return "redirect:/students";
//    }
//    @PostMapping( "/remove")
//    public String removeEnrollment(@RequestParam int studentId, @RequestParam int moduleId, RedirectAttributes redirectAttributes) {
//        String result = enrollmentService.removeEnrollmentFromModule(studentId, moduleId);
//        redirectAttributes.addFlashAttribute("message", result);
//        return "redirect:/students/" + studentId;
//    }

}
