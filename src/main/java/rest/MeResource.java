package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.user.PrivateUserDto;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("me")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MeResource {

    @Context
    SecurityContext securityContext;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final UserFacade USER_FACADE = UserFacade.getInstance(EMF);

    public MeResource() {}

    @GET
    @RolesAllowed("user")
    public Response getMe() {
        String username = securityContext.getUserPrincipal().getName();
        PrivateUserDto me = USER_FACADE.getPrivate(username);
        return Response.ok().entity(GSON.toJson(me)).build();
    }

}