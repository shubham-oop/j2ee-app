package com.example.student;

import com.example.config.DatabaseManager;
import com.example.student.dao.StudentDao;
import com.example.student.entity.Student;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.postgresql.util.PSQLException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/student")
public class StudentManager {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public List<Student> getStudentList() {
        List<Student> students = new ArrayList<>();
        students = DatabaseManager.getReadOnlyJdbi().withExtension(StudentDao.class, StudentDao::findAll);
        return students;
    }


    @GET
    @Path("/{roll_number}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentByRollNumber(@PathParam("roll_number") Integer roll_number) {
        try {
            Student student = DatabaseManager.getReadOnlyJdbi().withExtension(StudentDao.class,
                    dao -> dao.findByRollNumber(roll_number));

            if (student != null) {
                return Response.ok(student).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Student not found")
                        .build();
            }
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Error retrieving student: " + e.getMessage())
                    .build();
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addStudent(Student s) {
        try {
            DatabaseManager.getReadWriteJdbi().useTransaction(handle -> {
                StudentDao dao = handle.attach(StudentDao.class);
                int generatedId = dao.insertStudent(s);  // Get the ID directly
                s.setId(generatedId);  // Update your student object
            });
            return Response.status(Response.Status.CREATED)
                    .entity(s.getName() + " is " + " added to Database with id = " + s.getId())
                    .build();
        }catch (UnableToExecuteStatementException e) {
            if (e.getCause() instanceof PSQLException &&
                    e.getCause().getMessage().contains("duplicate key value")) {
                return Response.status(Response.Status.CONFLICT)
                        .entity(String.format("Student with roll number %d already exists.", s.getRoll_number()))
                        .build();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Something went wrong.")
                    .build();
        }catch (Exception e){
            return Response.serverError()
                    .entity("Error retrieving student: " + e.getMessage())
                    .build();
        }
    }

}
