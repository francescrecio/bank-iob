package com.iobuilder.user.infrastructure.exception;

import com.iobuilder.user.domain.exception.UserNotExistsException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserNotExistsExceptionHandler implements ExceptionMapper<UserNotExistsException> {

    @Override
    public Response toResponse(UserNotExistsException e) {
        return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }
}
