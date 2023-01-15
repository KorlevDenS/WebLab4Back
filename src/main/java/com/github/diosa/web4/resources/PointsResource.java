package com.github.diosa.web4.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.diosa.web4.models.Point;
import com.github.diosa.web4.secure.JWTTokenNeeded;
import com.github.diosa.web4.services.PointService;
import com.github.diosa.web4.services.UserService;
import org.json.JSONArray;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/points")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PointsResource {
    @Inject
    private PointService pointService;
    @Context
    private HttpServletRequest httpServletRequest;

    @JWTTokenNeeded
    @POST
    public Response createPoint(String json) throws JsonProcessingException {
        ObjectMapper obj = new ObjectMapper();
        Point point = obj.readValue(json, Point.class);

        return Response.ok()
                .entity(pointService.create(point, this.getUsernameFromHeader())
                ).build();
    }

    @JWTTokenNeeded
    @GET
    public Response getPoints(String json) {
        List<Point> points = pointService.getAllByUsername(this.getUsernameFromHeader());
        System.out.println(points);
        if (points.isEmpty()) return Response.noContent().build();

        return Response.ok().entity(points).build();
    }

    @JWTTokenNeeded
    @DELETE
    public Response clearPoints(String json) {
        String username = this.getUsernameFromHeader();
        pointService.deleteAllByUsername(username);
        return Response.ok().build();
    }

    private String getUsernameFromHeader() {
        return httpServletRequest.getAttribute("username").toString();
    }
}
