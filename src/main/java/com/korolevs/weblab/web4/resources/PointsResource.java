package com.korolevs.weblab.web4.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korolevs.weblab.web4.ResponseFactory;
import com.korolevs.weblab.web4.model.Point;
import com.korolevs.weblab.web4.secure.JWTTokenNeeded;
import com.korolevs.weblab.web4.services.PointService;

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

        return ResponseFactory.createSuccessResponse(pointService.create(point, this.getUsernameFromHeader()));
    }

    @JWTTokenNeeded
    @GET
    public Response getPoints(String json) {
        List<Point> points = pointService.getAllByUsername(this.getUsernameFromHeader());
        System.out.println(points);
        if (points.isEmpty()) return ResponseFactory.createResponse(204);

        return ResponseFactory.createSuccessResponse(points);
    }

    @JWTTokenNeeded
    @DELETE
    public Response clearPoints(String json) {
        String username = this.getUsernameFromHeader();
        pointService.deleteAllByUsername(username);
        return ResponseFactory.createSuccessResponse();
    }

    private String getUsernameFromHeader() {
        return httpServletRequest.getAttribute("username").toString();
    }
}
