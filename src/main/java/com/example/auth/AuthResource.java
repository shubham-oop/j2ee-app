package com.example.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;


@Path("/auth")
public class AuthResource {
    private static final Logger logger = LoggerFactory.getLogger(AuthResource.class);
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("username") String username,
                          @FormParam("password") String password,
                          @Context HttpServletRequest request) {

        if ("admin".equals(username) && "admin123".equals(password)) {
            HttpSession session = request.getSession(); // Get or create session
            session.setAttribute("user", username);
            session.setAttribute("role", "admin");
            session.setMaxInactiveInterval(30 * 60); // 30 minutes

            try {
                return Response.seeOther(new URI(request.getContextPath() + "/index.jsp")).build();
            } catch (URISyntaxException e) {
                // Log the error and return an internal server error response
                logger.error("Error form admin {}", Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error redirecting after successful login.")
                        .build();
            }
        } else {
            try {
                return Response.seeOther(new URI(request.getContextPath() + "/login.jsp?error=invalid")).build();
            } catch (URISyntaxException e) {
                logger.error("Error while logout: {}",Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error redirecting after failed login.")
                        .build();
            }
        }
    }

    @POST
    @Path("/logout")
    public Response logout(@Context HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        try {
            return Response.seeOther(new URI(request.getContextPath() + "/login.jsp")).build();
        } catch (URISyntaxException e) {
            logger.error("Error while logout: {}",Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error redirecting after logout.")
                    .build();
        }
    }
}