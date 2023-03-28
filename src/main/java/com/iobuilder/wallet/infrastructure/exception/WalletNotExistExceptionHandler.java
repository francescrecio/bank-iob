package com.iobuilder.wallet.infrastructure.exception;

import com.iobuilder.wallet.domain.exception.WalletNotExistException;
import com.iobuilder.wallet.domain.exception.WalletNotOwnedUserException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WalletNotExistExceptionHandler implements ExceptionMapper<WalletNotExistException> {

    @Override
    public Response toResponse(WalletNotExistException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
}
