package com.example.srms.repository;

import com.example.srms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * ===========================================================================
 * REPOSITORY LAYER
 * ===========================================================================
 * Data access layer. Spring Data JPA generates the implementation of this
 * interface at runtime, giving us CRUD methods (save, findById, findAll,
 * deleteById, etc.) for free, plus derived query methods we declare here
 * simply by naming convention.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Case-insensitive "contains" search by name, backing
     * GET /api/students/search?name=
     */
    List<Student> findByNameContainingIgnoreCase(String name);

    /**
     * Used by the service layer to enforce roll-number uniqueness on create/update.
     */
    boolean existsByRollNumber(String rollNumber);

    boolean existsByRollNumberAndIdNot(String rollNumber, Long id);
}
