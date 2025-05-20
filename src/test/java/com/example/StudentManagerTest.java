package com.example;

import com.example.student.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentManagerTest {

    private Student student;

    @BeforeEach
    public void setup() {
        student = new Student(null, 20, "Shubham", 3, 101L, "Delhi", "shubham@example.com");
    }

    @Test
    public void testAddStudent() {
        assertEquals("Shubham", student.getName()); // Adjust as per actual logic
    }
}
