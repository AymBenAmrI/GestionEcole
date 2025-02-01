package com.project.gestionecole.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "moduleId", referencedColumnName = "id")
    private Module module;

    private float mark;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true, nullable = false)
    private Status status;

    private String semester;

    private Date enrollmentDate;

    private int academicYear;
    private Date createdAt;

    private Date updatedAt;

    public Enrollment() {
    }

    public Enrollment(int id, Student student, Module module, float mark, Status status, String semester, Date enrollmentDate, int academicYear, Date createdAt, Date updatedAt) {
        this.id = id;
        this.student = student;
        this.module = module;
        this.mark = mark;
        this.status = status;
        this.semester = semester;
        this.enrollmentDate = enrollmentDate;
        this.academicYear = academicYear;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
