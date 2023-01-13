package com.github.diosa.web4.exceptions.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.diosa.web4.data.ExceptionResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonProcessingExceptionMapper implements ExceptionMapper<JsonProcessingException> {
    @Override
    public Response toResponse(JsonProcessingException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ExceptionResponse(e.getClass().getName(), e.getMessage()))
                .build();
    }
}
