package com.example.student;

import com.example.student.entity.Student;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/students")
public class StudentResource {

    @GET
    @RolesAllowed("admin") // Requires admin role
    public Response getAllStudents(@Context HttpSession session) {
        // Your existing student retrieval logic
        List<Student> studentList = new StudentManager().getStudentList();
        return Response.ok(studentList).build();
    }

//    @POST
//    @RolesAllowed("admin")
//    public Response addStudent(Student student, @Context HttpSession session) {
//        // Your existing student creation logic
//        return Response.status(Response.Status.CREATED).entity(createdStudent).build();
//    }
}
