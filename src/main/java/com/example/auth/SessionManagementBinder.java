package com.example.auth;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

class SessionFactory implements Factory<HttpSession> {
    @Context
    private HttpServletRequest request;

    @Override
    public HttpSession provide() {
        return request.getSession();
    }

    @Override
    public void dispose(HttpSession session) {
        // Session cleanup if needed
    }
}

public class SessionManagementBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bindFactory(SessionFactory.class).to(HttpSession.class)
                .proxy(true)
                .proxyForSameScope(false)
                .in(RequestScoped.class);
    }
}

