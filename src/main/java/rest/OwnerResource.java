/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.OwnerFacade;
import facades.UserFacade;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author Mathias
 */
@Path("owner")
public class OwnerResource {

    @Context
    SecurityContext securityContext;
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final OwnerFacade OWNER_FACADE = OwnerFacade.getInstance(EMF);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Creates a new instance of OwnerResource
     */
    public OwnerResource() {
    }

    @GET
    @Path("all")
    @RolesAllowed("user")
    public Response getAllOwners() {
        return Response.ok(GSON.toJson(OWNER_FACADE.getAllOwners())).build();
    }
}
