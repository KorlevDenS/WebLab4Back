package com.korolevs.weblab.web4.exceptions;

import lombok.Builder;
import lombok.Getter;

import javax.ejb.ApplicationException;
import javax.ws.rs.core.Response;

@Getter
@ApplicationException
public class ApiException extends RuntimeException {
    private final Response.Status status;

    public ApiException(String message, Response.Status status) {
        super(message);
        this.status = status;
    }

}