package com.example.auth;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.ext.Provider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    @Context
    private HttpServletRequest webRequest;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        String method = requestContext.getMethod();
        if (path.startsWith("student") && (method.equals("POST") || method.equals("DELETE"))) {
            HttpSession session = webRequest.getSession(false);
            String role = (session != null) ? (String) session.getAttribute("role") : null;

            if (!"admin".equals(role)) {
                requestContext.abortWith(Response
                        .status(Response.Status.FORBIDDEN)
                        .entity("Admin access required")
                        .build());
            }
        }
    }
}
