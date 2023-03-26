package com.iobuilder.wallet.infrastructure;

import com.iobuilder.user.domain.User;
import com.iobuilder.wallet.application.WalletCreator;
import com.iobuilder.wallet.application.WalletDTO;
import com.iobuilder.wallet.application.WalletDeposit;
import com.iobuilder.wallet.application.WalletMovements;
import com.iobuilder.wallet.domain.Wallet;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.net.URI;

@Path("/wallet")
public class WalletController {

    @Inject
    WalletCreator walletCreator;

    @Inject
    WalletDeposit walletDeposit;

    @Inject
    WalletMovements walletMovements;

    @POST
    @Path("/create")
    @RolesAllowed("User")
    public Response create(@Context SecurityContext ctx) {
        Wallet wallet = walletCreator.create(ctx.getUserPrincipal().getName());
        return Response.created(URI.create("/wallet/".concat(wallet.getId()))).entity(wallet).build();
    }

    @PUT
    @Path("/deposit/{walletId}")
    @RolesAllowed("User")
    public Response deposit(@Context SecurityContext ctx, @PathParam("walletId") String walletId, @QueryParam("amount") Float amount) {
       //TODO CHECK IF EXIST THE WALLET
        walletDeposit.deposit(walletId, amount);
        return Response.ok().build();
    }

    @GET
    @Path("/balance/{walletId}")
    @RolesAllowed("User")
    public Response balance(@Context SecurityContext ctx, @PathParam("walletId") String walletId) {
        //TODO Check if user is the owner
        WalletDTO walletDTO = walletMovements.movements(walletId);
        return Response.ok().entity(walletDTO).build();
    }
}
