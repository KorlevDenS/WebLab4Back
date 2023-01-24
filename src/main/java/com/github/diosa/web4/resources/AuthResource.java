package com.github.diosa.web4.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.diosa.web4.data.UserResponse;
import com.github.diosa.web4.factory.ResponseFactory;
import com.github.diosa.web4.models.User;
import com.github.diosa.web4.secure.JWTTokenNeeded;
import com.github.diosa.web4.services.impl.UserServiceImpl;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/auth")
public class AuthResource {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Inject
    private UserServiceImpl userService;
    @Context
    private HttpServletRequest httpServletRequest;

    @POST
    @Path("/register")
    public Response register(String json) throws JsonProcessingException {
        User user = objectMapper.readValue(json, User.class);

        userService.register(user.toBuilder()
                .authenticated(true)
                .build());
        String token = userService.issueToken(user.getUsername());

        return ResponseFactory.createSuccessResponse(new UserResponse(token));
    }

    @POST
    @Path("/login")
    public Response login(String json) throws JsonProcessingException {
        User user = objectMapper.readValue(json, User.class);

        userService.login(user.toBuilder()
                .authenticated(true)
                .build());
        String token = userService.issueToken(user.getUsername());

        return ResponseFactory.createSuccessResponse(new UserResponse(token));
    }

    @POST
    @JWTTokenNeeded
    @Path("/logout")
    public Response logout() {
        userService.logout(this.getUsernameFromHeader());
        return ResponseFactory.createSuccessResponse();
    }


    private String getUsernameFromHeader() {
        return httpServletRequest.getAttribute("username").toString();
    }
}
