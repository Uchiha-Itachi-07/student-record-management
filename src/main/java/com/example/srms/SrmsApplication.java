package com.example.srms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the Student Record Management System (SRMS) backend.
 *
 * Running this class starts an embedded Tomcat server on port 8080, wires up
 * the Spring context (Controller -> Service -> Repository -> Model layers),
 * connects to the H2 in-memory database, and seeds sample data via
 * {@link com.example.srms.config.DataSeeder}.
 */
@SpringBootApplication
public class SrmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrmsApplication.class, args);
    }
}
