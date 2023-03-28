package com.iobuilder.wallet.infrastructure.exception;

import com.iobuilder.wallet.domain.exception.WalletNotOwnedUserException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WalletNotOwnedUserExceptionHandler implements ExceptionMapper<WalletNotOwnedUserException> {

    @Override
    public Response toResponse(WalletNotOwnedUserException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
}
