package com.korolevs.weblab.web4.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korolevs.weblab.web4.responses.UserResponse;
import com.korolevs.weblab.web4.ResponseFactory;
import com.korolevs.weblab.web4.model.User;
import com.korolevs.weblab.web4.secure.JWTTokenNeeded;
import com.korolevs.weblab.web4.services.impl.UserServiceImpl;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
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

        userService.register(user);
        String token = userService.issueToken(user.getUsername());

        return ResponseFactory.createSuccessResponse(new UserResponse(token));
    }

    @POST
    @Path("/login")
    public Response login(String json) throws JsonProcessingException {
        User user = objectMapper.readValue(json, User.class);

        userService.login(user);
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

    @GET
    @JWTTokenNeeded
    @Path("/check-login")
    public Response checkLogin() {
        return ResponseFactory.createSuccessResponse(this.getUsernameFromHeader());
    }


    private String getUsernameFromHeader() {
        return httpServletRequest.getAttribute("username").toString();
    }
}
