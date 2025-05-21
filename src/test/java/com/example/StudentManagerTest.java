package com.example;

import com.example.student.entity.Student;
import com.example.student.entity.StudentClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentManagerTest {

    private Student student;

    @BeforeEach
    public void setup() {
        student = new Student(null, 20, "Shubham", 3, 101L, "Delhi", "shubham@example.com", StudentClass.CLASS_5);
    }

    @Test
    public void testAddStudent() {
        System.out.println(student.getStudentClass().getDbValue());
        assertEquals("Shubham", student.getName());
    }
}
