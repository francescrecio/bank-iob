package com.iobuilder.transfer.infrastructure.exception;

import com.iobuilder.transfer.domain.exception.InsufficientBalanceException;
import com.iobuilder.user.domain.exception.UserAlreadyExistsException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InsufficientBalanceExceptionHandler implements ExceptionMapper<InsufficientBalanceException> {

    @Override
    public Response toResponse(InsufficientBalanceException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
}
