package com.github.diosa.web4.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.diosa.web4.filter.JWTTokenNeeded;
import com.github.diosa.web4.models.Point;
import com.github.diosa.web4.services.PointService;
import com.github.diosa.web4.services.UserService;
import org.json.JSONArray;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/points")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PointsResource {
    @Inject
    private PointService pointService;
    @Inject
    private UserService userService;

    @Context
    private HttpServletRequest httpServletRequest;

    @JWTTokenNeeded
    @POST
    public Response createPoint(String json) throws JsonProcessingException {
        String username = this.getUsernameFromHeader();
        ObjectMapper obj = new ObjectMapper();
        Point point = obj.readValue(json, Point.class);

        return Response.ok()
                .entity(
                        new JSONArray(pointService.create(point, userService.getUser(username)))
                ).build();
    }

    @JWTTokenNeeded
    @GET
    public Response getPoints(String json) {
        String username = this.getUsernameFromHeader();
        return Response.ok()
                .entity(
                        new JSONArray(pointService.getAllByUsername(username))
                ).build();
    }

    private String getUsernameFromHeader() {
        return httpServletRequest.getAttribute("username").toString();
    }
}
