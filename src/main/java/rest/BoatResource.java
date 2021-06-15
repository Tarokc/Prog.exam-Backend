/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BoatDTO;
import facades.BoatFacade;
import facades.OwnerFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author Mathias
 */
@Path("boat")
public class BoatResource {

@Context
    SecurityContext securityContext;
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final BoatFacade BOAT_FACADE = BoatFacade.getInstance(EMF);
    private static final OwnerFacade OWNER_FACADE = OwnerFacade.getInstance(EMF);

    
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    public BoatResource() {
        
    }
    
    @POST
    public Response register(String json) {
        BoatDTO newBoat = GSON.fromJson(json, BoatDTO.class);
        System.out.println(json);
        newBoat = BOAT_FACADE.register(newBoat);
        return Response.ok(GSON.toJson(newBoat)).build();
    }
    
    @GET
    public Response getAllBoats() {
        List<BoatDTO> boats = BOAT_FACADE.getAllBoats();
        if (!boats.isEmpty()) {
            return Response.ok(GSON.toJson(boats)).build();
        }
        return null;
    }
    
    
    @GET
    @Path("/{name}")
    public Response getOwnersByBoatname(@PathParam("name") String name) {
        return Response.ok(GSON.toJson(OWNER_FACADE.getOwnerByBoatName(name))).build();
    }
}
