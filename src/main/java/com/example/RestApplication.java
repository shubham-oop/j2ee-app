package com.example;

import com.example.auth.AuthenticationFilter;
import com.example.auth.AuthorizationFilter;
import com.example.auth.SessionManagementBinder;
import com.example.config.DatabaseManager;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class RestApplication extends ResourceConfig {
    public RestApplication() {
        packages("com.example");
        register(JacksonFeature.class);
        register(new ObjectMapperProvider()); // Add this class (see below)

        // Register security filters
        register(AuthenticationFilter.class);
        register(AuthorizationFilter.class);

        // Register other features
        register(RolesAllowedDynamicFeature.class); // For @RolesAllowed support
        register(new SessionManagementBinder());

        // Initialize global DB connections (read-only and read-write)
        DatabaseManager.initialize();

    }
}
