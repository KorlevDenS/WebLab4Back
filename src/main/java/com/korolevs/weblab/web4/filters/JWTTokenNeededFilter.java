package com.korolevs.weblab.web4.filters;

import com.korolevs.weblab.web4.exceptions.ApiException;
import com.korolevs.weblab.web4.secure.JWTTokenNeeded;
import com.korolevs.weblab.web4.secure.KeyGenerator;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {

    @Inject
    private KeyGenerator keyGenerator;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
            throw new ApiException("Вы не вошли в ситсему", Response.Status.METHOD_NOT_ALLOWED);

        String name = keyGenerator.decodeKey(authorizationHeader);

        if (name == null) throw new ApiException("Вы не вошли в ситсему", Response.Status.METHOD_NOT_ALLOWED);

        requestContext.setProperty("username", name);
    }
}
