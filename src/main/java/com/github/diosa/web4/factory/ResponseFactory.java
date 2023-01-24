package com.github.diosa.web4.factory;

import javax.ws.rs.core.Response;

public class ResponseFactory {

    private ResponseFactory() {

    }

    public static Response createSuccessResponse() {
        return createResponse(200, null);
    }

    public static Response createSuccessResponse(Object entity) {
        return createResponse(200, entity);
    }

    public static Response createResponse(int status, Object entity) {
        return Response
                .status(status)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .entity(entity)
                .build();
    }

}
