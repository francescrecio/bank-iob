package com.iobuilder.wallet.infrastructure;

import com.iobuilder.shared.application.security.OwnerChecker;
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

    @Inject
    OwnerChecker ownerChecker;

    @POST
    @Path("/create")
    @RolesAllowed("User")
    public Response create(@Context SecurityContext ctx) {
        WalletDTO wallet = walletCreator.create(ctx.getUserPrincipal().getName());
        return Response.created(URI.create("/wallet/".concat(wallet.getId()))).entity(wallet).build();
    }

    @PUT
    @Path("/deposit/{walletId}")
    @RolesAllowed("User")
    public Response deposit(@Context SecurityContext ctx, @PathParam("walletId") String walletId, @QueryParam("amount") Float amount) {
        walletDeposit.deposit(walletId, amount);
        return Response.ok().build();
    }

    @GET
    @Path("/balance/{walletId}")
    @RolesAllowed("User")
    public Response balance(@Context SecurityContext ctx, @PathParam("walletId") String walletId) {
        ownerChecker.checkIsTheOwner(walletId, ctx.getUserPrincipal().getName());
        WalletDTO walletDTO = walletMovements.movements(walletId);
        return Response.ok().entity(walletDTO).build();
    }
}
