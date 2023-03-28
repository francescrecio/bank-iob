package com.iobuilder.user.infrastructure;

import com.iobuilder.user.application.UserCreator;
import com.iobuilder.user.application.UserDTO;
import com.iobuilder.user.application.UserLogin;
import com.iobuilder.user.domain.User;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/user")
public class UserController {

    @Inject
    UserCreator userCreator;

    @Inject
    UserLogin userLogin;

    @POST
    @Path("/create")
    public Response create(@QueryParam("username") String username, @QueryParam("password") String password) {
        UserDTO user = userCreator.create(username, password);
        return Response.created(URI.create("/user/".concat(user.getId()))).entity(user).build();
    }

    @POST
    @Path("/login")
    public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {
        String token = userLogin.login(username, password);
        return Response.ok().entity(token).build();
    }

}
