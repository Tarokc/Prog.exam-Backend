package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.user.PrivateUserDto;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    @Context
    SecurityContext securityContext;

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final UserFacade USER_FACADE = UserFacade.getInstance(EMF);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public UserResource() {}

    @PUT
    @RolesAllowed("user")
    public Response updateUser(String json) {
        PrivateUserDto updatedUser = GSON.fromJson(json, PrivateUserDto.class);
        // Ensure that we only update our signed in user.
        updatedUser.setUsername(securityContext.getUserPrincipal().getName());

        updatedUser = USER_FACADE.updateUser(updatedUser);
        return Response.ok(GSON.toJson(updatedUser)).build();
    }
}
