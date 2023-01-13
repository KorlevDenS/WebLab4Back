package com.github.diosa.web4.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.diosa.web4.data.UserResponse;
import com.github.diosa.web4.models.User;
import com.github.diosa.web4.services.impl.UserServiceImpl;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/auth")
public class AuthResource {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Inject
    private UserServiceImpl userService;

    @POST
    @Path("/register")
    public Response register(String json) throws JsonProcessingException {
        User user = objectMapper.readValue(json, User.class);

        User result = userService.register(user);
        String token = userService.issueToken(user.getUsername());

        return Response.ok().entity(
                        new UserResponse(token, result))
                .build();
    }

    @POST
    @Path("/login")
    public Response login(String json) throws JsonProcessingException {
        User user = objectMapper.readValue(json, User.class);

        User result = userService.login(user);
        String token = userService.issueToken(user.getUsername());

        return Response.ok().entity(
                        new UserResponse(token, result))
                .build();
    }
}
