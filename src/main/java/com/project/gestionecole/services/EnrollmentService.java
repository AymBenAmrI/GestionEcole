package com.project.gestionecole.services;


import com.project.gestionecole.dtos.CancelEnrollmentRequest;
import com.project.gestionecole.dtos.EnrollStudentRequest;
import com.project.gestionecole.models.Enrollment;
import com.project.gestionecole.models.Module;
import com.project.gestionecole.models.Student;
import com.project.gestionecole.models.Status;
import com.project.gestionecole.respositories.EnrollmentRepository;
import com.project.gestionecole.respositories.ModuleRepository;
import com.project.gestionecole.respositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    // Register student to a module
    public String enrollStudent(EnrollStudentRequest request) {
        Optional<Student> studentOptional = studentRepository.findById(request.getStudentId());
        Optional<Module> moduleOptional = moduleRepository.findById(request.getModuleId());

        if (studentOptional.isEmpty() || moduleOptional.isEmpty()) {
            return "Invalid student or module ID.";
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(studentOptional.get());
        enrollment.setModule(moduleOptional.get());
        enrollment.setSemester(request.getSemester());
        enrollment.setAcademicYear(request.getAcademicYear());
        enrollment.setEnrollmentDate(new Date());
        enrollment.setStatus(Status.ENROLLED); // Assuming Status is an enum
        enrollment.setCreatedAt(new Date());
        enrollment.setUpdatedAt(new Date());

        enrollmentRepository.save(enrollment);

        return "Student enrolled successfully.";
    }
    public String enrollStudentsInModule(List<Integer> studentIds, int moduleId) {
        Module module = moduleRepository.getReferenceById(moduleId);
        if (module == null) {
            return "Module not found";
        }

        for (Integer studentId : studentIds) {
            Enrollment enrollmnt = enrollmentRepository.findByStudentIdAndModuleId(studentId, moduleId) ;
            Student student = studentRepository.getReferenceById(studentId);
            if (enrollmnt != null && enrollmnt.getStatus() != Status.INVALIDATED) {
                return "error : Students already enrolled!";
            }
            if (student != null) {
                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(student);
                enrollment.setModule(module);
                enrollment.setStatus(Status.ENROLLED);  // directly use the enum constant
                enrollment.setEnrollmentDate(new Date());  // set current date and time
                enrollmentRepository.save(enrollment);
            }
        }
        return "Students enrolled successfully!";
    }
    public String removeEnrollmentFromModule(int studentId, int moduleId) {
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndModuleId(studentId, moduleId);
        if (enrollment != null) {
            enrollmentRepository.delete(enrollment);
            return "Enrollment removed successfully";
        }
        return "Enrollment not found";
    }

    // Cancel an enrollment
//    public String cancelEnrollment(CancelEnrollmentRequest request) {
//        Optional<Enrollment> enrollmentOptional = enrollmentRepository.findById(request.getEnrollmentId());
//
//        if (enrollmentOptional.isEmpty()) {
//            return "Enrollment not found.";
//        }
//
//        Enrollment enrollment = enrollmentOptional.get();
//        enrollmentRepository.delete(enrollment);
//
//        return "Enrollment canceled successfully.";
//    }

    // Get enrollments by module
    public List<Enrollment> getEnrollmentsByModule(int moduleId) {
        return enrollmentRepository.findByModuleId(moduleId);
    }
    public long countEnrollments() {
        return enrollmentRepository.count();
    }

}

