package com.example;

import com.example.config.DatabaseManager;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class RestApplication extends ResourceConfig {
    public RestApplication() {
        packages("com.example");
        register(JacksonFeature.class);
        register(new ObjectMapperProvider()); // Add this class (see below)

        // Initialize global DB connections (read-only and read-write)
        DatabaseManager.initialize();

    }
}
