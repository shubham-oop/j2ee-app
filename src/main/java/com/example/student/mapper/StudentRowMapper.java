package com.example.student.mapper;

import com.example.student.entity.Student;
import com.example.student.entity.StudentClass;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {
    public StudentRowMapper() {
    }

    @Override
    public Student map(ResultSet rs, StatementContext ctx) throws SQLException {
        String classDbValue = rs.getString("class");

        StudentClass studentClassEnum = null;
        if (classDbValue != null) {
            studentClassEnum = StudentClass.fromDbValue(classDbValue);
        }

        return new Student(
                rs.getInt("id"),
                rs.getInt("roll_number"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getLong("phone_number"),
                rs.getString("email"),
                rs.getString("address"),
                studentClassEnum
        );
    }
}