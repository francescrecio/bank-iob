package com.iobuilder.transfer.infrastructure;

import com.iobuilder.shared.application.security.OwnerChecker;
import com.iobuilder.transfer.application.TransferCreator;
import com.iobuilder.transfer.application.TransferDTO;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/transfer")
public class TransferController {

    @Inject
    TransferCreator transferCreator;

    @Inject
    OwnerChecker ownerChecker;

    @POST
    @Path("/create")
    @RolesAllowed("User")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Context SecurityContext ctx, @RequestBody TransferDTO transferDTO) {
        ownerChecker.checkIsTheOwner(transferDTO.getWalletOrigin(), ctx.getUserPrincipal().getName());

        return Response.ok().entity(transferCreator.create(transferDTO)).build();
    }

}
