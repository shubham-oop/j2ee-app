package com.example.student.dao;

import com.example.student.entity.Student;
import com.example.student.mapper.StudentRowMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.*;

import java.util.List;

@RegisterRowMapper(StudentRowMapper.class)
public interface StudentDao {

    @SqlQuery("SELECT * FROM students ORDER BY roll_number ASC")
    List<Student> findAll();
//
//    @SqlQuery("SELECT * FROM student WHERE rollNumber IN (<rollNumbers>)")
//    List<Student> filterByIds(@BindList("rollNumbers") List<Integer> rollNumbers);
//
    @SqlQuery("SELECT * FROM students WHERE roll_number = :roll_number")
    Student findByRollNumber(@Bind("roll_number") int roll_number);

//    @SqlQuery("SELECT id, roll_number as rollNumber, name, age, " +
//            "phone_number as phoneNumber, email, address " +
//            "FROM students WHERE roll_number = :rollNumber")
//    @RegisterBeanMapper(Student.class)
//    Student findByRollNumber(@Bind("rollNumber") int rollNumber);

    @SqlUpdate("INSERT INTO students (roll_number, name, age, phone_number, address, email, class) " +
            "VALUES (:roll_number, :name, :age, :phone_number, :address, :email, CAST(:classDbValue AS student_class))")
    @GetGeneratedKeys
    int insertStudent(@BindBean Student student, @Bind("classDbValue") String classDbValue);

//    @SqlBatch("INSERT INTO student (rollNumber, name, age, grade, address, email) VALUES (:rollNumber, :name, :age, :grade, :address, :email)")
//    void insertStudents(@BindBean List<Student> students);
//
//    @SqlUpdate("UPDATE student SET name = :name, age = :age, grade = :grade, address = :address, email = :email WHERE rollNumber = :rollNumber")
//    void updateStudent(@BindBean Student student);
//
//    @SqlUpdate("DELETE FROM student WHERE rollNumber = :rollNumber")
//    void deleteStudent(@Bind("rollNumber") int rollNumber);
//
//    @SqlBatch("DELETE FROM student WHERE rollNumber = :rollNumber")
//    void deleteStudentsById(@Bind("rollNumber") List<Integer> rollNumbers);
//
//    @SqlBatch("INSERT INTO student (name, rollNumber, age, grade, address, email) VALUES (:name, :rollNumber, :age, :grade, :address, :email)")
//    void insertStudentsWithoutId(@BindBean List<Student> students); // assumes id is auto-generated
//
//    @SqlBatch("INSERT INTO student (name, rollNumber, age, grade, address, email) VALUES (:name, :rollNumber, :age, :grade, :address, :email)")
//    @GetGeneratedKeys
//    List<Student> insertAndReturn(@BindBean List<Student> students);
}
