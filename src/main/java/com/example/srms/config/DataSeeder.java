package com.example.srms.config;

import com.example.srms.model.Student;
import com.example.srms.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Seeds the H2 in-memory database with sample students on every startup
 * (only if the table is empty), so the frontend has something to display
 * immediately without any manual setup.
 */
@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedStudents(StudentRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }

            repository.save(new Student("Aarav Sharma", "R001", "aarav.sharma@example.com", "Computer Science", 2, 8.7));
            repository.save(new Student("Priya Nair", "R002", "priya.nair@example.com", "Electronics", 3, 9.1));
            repository.save(new Student("Rohan Gupta", "R003", "rohan.gupta@example.com", "Mechanical", 1, 7.5));
            repository.save(new Student("Sneha Iyer", "R004", "sneha.iyer@example.com", "Computer Science", 4, 9.4));
            repository.save(new Student("Vikram Singh", "R005", "vikram.singh@example.com", "Civil", 2, 6.8));
            repository.save(new Student("Ananya Reddy", "R006", "ananya.reddy@example.com", "Information Technology", 3, 8.2));
            repository.save(new Student("Karan Mehta", "R007", "karan.mehta@example.com", "Electrical", 1, 7.9));
            repository.save(new Student("Ishita Verma", "R008", "ishita.verma@example.com", "Computer Science", 2, 8.9));
        };
    }
}
