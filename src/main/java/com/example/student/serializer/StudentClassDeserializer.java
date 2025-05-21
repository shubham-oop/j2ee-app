package com.example.student.serializer;

import com.example.student.entity.StudentClass;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class StudentClassDeserializer extends JsonDeserializer<StudentClass> {
    @Override
    public StudentClass deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dbValue = p.getText();
        if (dbValue == null || dbValue.trim().isEmpty()) {
            return null;
        }
        return StudentClass.fromDbValue(dbValue); // Use your enum's static factory method
    }
}
