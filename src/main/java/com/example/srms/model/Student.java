package com.example.srms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * ===========================================================================
 * MODEL LAYER
 * ===========================================================================
 * This is the JPA entity that maps directly onto the `students` table.
 * It also carries Bean Validation annotations (@NotBlank, @Email, etc.) so
 * that the Controller layer can validate incoming JSON before it ever
 * reaches the Service/Repository layers (see @Valid in StudentController).
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Roll number is required")
    @Column(nullable = false, unique = true)
    private String rollNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "Department is required")
    @Column(nullable = false)
    private String department;

    @NotNull(message = "Year is required")
    @Min(value = 1, message = "Year must be between 1 and 6")
    @Max(value = 6, message = "Year must be between 1 and 6")
    @Column(name = "student_year", nullable = false)
   private Integer year;

    @NotNull(message = "GPA is required")
    @DecimalMin(value = "0.0", message = "GPA must be between 0 and 10")
    @DecimalMax(value = "10.0", message = "GPA must be between 0 and 10")
    @Column(nullable = false)
    private Double gpa;

    public Student() {
        // Required no-arg constructor for JPA
    }

    public Student(String name, String rollNumber, String email, String department, Integer year, Double gpa) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.email = email;
        this.department = department;
        this.year = year;
        this.gpa = gpa;
    }

    // ------------------------------------------------------------------
    // Getters and setters
    // ------------------------------------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }
}
