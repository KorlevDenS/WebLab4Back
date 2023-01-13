package com.github.diosa.web4.exceptions.mapper;

import com.github.diosa.web4.data.ExceptionResponse;
import com.github.diosa.web4.exceptions.ApiException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ApiExceptionMapper implements ExceptionMapper<ApiException> {
    @Override
    public Response toResponse(ApiException e) {
        return Response.status(e.getStatus())
                .entity(new ExceptionResponse(e.getClass().getName(), e.getMessage()))
                .build();
    }
}
