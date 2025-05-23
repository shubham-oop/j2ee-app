package com.example.student;

import com.example.config.DatabaseManager;
import com.example.student.dao.StudentDao;
import com.example.student.entity.Student;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

@Path("/student")
public class StudentManager {
    private static final Logger logger = LoggerFactory.getLogger(StudentManager.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public List<Student> getStudentList() {
        return DatabaseManager.getReadOnlyJdbi().withExtension(StudentDao.class, StudentDao::findAll);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentByRollNumber(@QueryParam("roll_number") Integer roll_number) {
        try {
            Student student = DatabaseManager.getReadOnlyJdbi().withExtension(StudentDao.class,
                    dao -> dao.findByRollNumber(roll_number));

            if (Objects.nonNull(student)) {
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
                int generatedId = dao.insertStudent(s, s.getStudentClass().getDbValue());  // Get the ID directly
                s.setId(generatedId);  // Update your student object
            });
            return Response.status(Response.Status.CREATED)
                    .entity(s.getName() + " is successfully added to Database.")
                    .build();
        }catch (UnableToExecuteStatementException e) {
            if (e.getCause() instanceof PSQLException &&
                    e.getCause().getMessage().contains("duplicate key value")) {
                return Response.status(Response.Status.CONFLICT)
                        .entity(String.format("Student with roll number %d already exists.", s.getRoll_number()))
                        .build();
            }
            logger.error("Unexpected database error while adding student: {}", s.toString(), e);
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
