package com.korolevs.weblab.web4.exceptions.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.korolevs.weblab.web4.responses.ExceptionResponse;
import com.korolevs.weblab.web4.ResponseFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonProcessingExceptionMapper implements ExceptionMapper<JsonProcessingException> {
    @Override
    public Response toResponse(JsonProcessingException e) {
        return ResponseFactory.createResponse(Response.Status.BAD_REQUEST.getStatusCode(),
                new ExceptionResponse(e.getClass().getName(), e.getMessage()));
    }
}
