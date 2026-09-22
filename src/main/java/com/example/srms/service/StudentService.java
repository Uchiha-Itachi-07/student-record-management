package com.example.srms.service;

import com.example.srms.exception.DuplicateRollNumberException;
import com.example.srms.exception.ResourceNotFoundException;
import com.example.srms.model.Student;
import com.example.srms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ===========================================================================
 * SERVICE LAYER
 * ===========================================================================
 * Holds business logic and orchestrates the Repository layer. Controllers
 * never talk to the Repository directly — everything goes through here,
 * which keeps validation/business rules (like "roll number must be unique")
 * in one place instead of scattered across endpoints.
 */
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    public List<Student> searchByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

    public Student createStudent(Student student) {
        if (studentRepository.existsByRollNumber(student.getRollNumber())) {
            throw new DuplicateRollNumberException(
                    "A student with roll number '" + student.getRollNumber() + "' already exists");
        }
        // Ensure id is null so JPA generates a new one even if the client sent one
        student.setId(null);
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student updated) {
        Student existing = getStudentById(id); // throws 404 if missing

        if (studentRepository.existsByRollNumberAndIdNot(updated.getRollNumber(), id)) {
            throw new DuplicateRollNumberException(
                    "A student with roll number '" + updated.getRollNumber() + "' already exists");
        }

        existing.setName(updated.getName());
        existing.setRollNumber(updated.getRollNumber());
        existing.setEmail(updated.getEmail());
        existing.setDepartment(updated.getDepartment());
        existing.setYear(updated.getYear());
        existing.setGpa(updated.getGpa());

        return studentRepository.save(existing);
    }

    public void deleteStudent(Long id) {
        Student existing = getStudentById(id); // throws 404 if missing
        studentRepository.delete(existing);
    }
}
