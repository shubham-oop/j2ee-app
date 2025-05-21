package com.example.student;

import com.example.student.entity.StudentClass;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Path("/classes")
public class ClassResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStudentClasses() {
        List<String> classDbValues = Arrays.stream(StudentClass.values())
                .map(StudentClass::getDbValue)
                .collect(Collectors.toList());
        return Response.ok(classDbValues).build();
    }
}