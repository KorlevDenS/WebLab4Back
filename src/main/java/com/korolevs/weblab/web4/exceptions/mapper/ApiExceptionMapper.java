package com.korolevs.weblab.web4.exceptions.mapper;

import com.korolevs.weblab.web4.responses.ExceptionResponse;
import com.korolevs.weblab.web4.exceptions.ApiException;
import com.korolevs.weblab.web4.ResponseFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ApiExceptionMapper implements ExceptionMapper<ApiException> {
    @Override
    public Response toResponse(ApiException e) {
        return ResponseFactory.createResponse(e.getStatus().getStatusCode(),
                new ExceptionResponse(e.getClass().getName(), e.getMessage()));
    }
}
