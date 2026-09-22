package com.example.srms.controller;

import com.example.srms.model.Student;
import com.example.srms.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ===========================================================================
 * CONTROLLER LAYER
 * ===========================================================================
 * Thin HTTP layer: parses requests, delegates to the Service layer, and maps
 * results onto HTTP status codes. No business logic lives here.
 *
 * CORS is enabled globally in {@link com.example.srms.config.CorsConfig}.
 * Deliberately no @CrossOrigin here — stacking it on top of the CorsFilter
 * bean causes duplicate Access-Control-Allow-Origin headers, which browsers
 * reject (see the note in CorsConfig).
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /** GET /api/students — list all students. 200 OK with a (possibly empty) array. */
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    /** GET /api/students/{id} — 200 OK + student, or 404 if not found (handled by GlobalExceptionHandler). */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    /**
     * GET /api/students/search?name=... — bonus endpoint, case-insensitive
     * "contains" search. 200 OK with matching students (empty array if none).
     */
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(studentService.searchByName(name));
    }

    /**
     * POST /api/students — create a new student.
     * @Valid triggers Bean Validation on the Student entity (see model/Student.java);
     * failures are caught by GlobalExceptionHandler and turned into 400 responses.
     * 201 Created on success.
     */
    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        Student saved = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /** PUT /api/students/{id} — update an existing student. 200 OK, or 404 if the id doesn't exist. */
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @Valid @RequestBody Student student) {
        Student updated = studentService.updateStudent(id, student);
        return ResponseEntity.ok(updated);
    }

    /** DELETE /api/students/{id} — 204 No Content on success, or 404 if the id doesn't exist. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
