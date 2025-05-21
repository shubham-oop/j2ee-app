package com.example.student.serializer;

import com.example.student.entity.StudentClass;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class StudentClassSerializer extends JsonSerializer<StudentClass> {
    @Override
    public void serialize(StudentClass value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            gen.writeString(value.getDbValue()); // Write the dbValue
        } else {
            gen.writeNull();
        }
    }
}
