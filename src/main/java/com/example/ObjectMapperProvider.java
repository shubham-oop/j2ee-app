package com.example;

import com.example.student.serializer.StudentClassDeserializer;
import com.example.student.serializer.StudentClassSerializer;
import com.example.student.entity.StudentClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {
    private final ObjectMapper mapper;

    public ObjectMapperProvider() {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        // Add other Jackson configs if needed

        SimpleModule module = new SimpleModule();

        // Register your custom serializer for the StudentClass enum
        module.addSerializer(StudentClass.class, new StudentClassSerializer());
        module.addDeserializer(StudentClass.class, new StudentClassDeserializer());
        mapper.registerModule(module);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}