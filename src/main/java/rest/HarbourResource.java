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
import facades.HarbourFacade;
import facades.OwnerFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
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
@Path("harbour")
public class HarbourResource {

@Context
    SecurityContext securityContext;
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final HarbourFacade HARBOUR_FACADE = HarbourFacade.getInstance(EMF);
    private static final BoatFacade BOAT_FACADE = BoatFacade.getInstance(EMF);
    
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @GET
    @Path("/{name}")
    public Response getBoatsByHarbour(@PathParam("name") String name) {
        
        int harbour_id = HARBOUR_FACADE.getHarbourId(name);
        System.out.println(name);
        
        return Response.ok(GSON.toJson(BOAT_FACADE.getBoatsInHarbour(harbour_id))).build();
    }
    
    @GET
    @Path("/names")
    public Response getHarbourNames() {
        return Response.ok(GSON.toJson(HARBOUR_FACADE.getHarbourNames())).build();
    }
    
    @PUT
    public Response connectBoat(String json) {
        String harbour = json.split(",")[1];
        harbour = harbour.substring(harbour.indexOf(":\"") + 2, harbour.lastIndexOf("\""));
        int harbour_id = HARBOUR_FACADE.getHarbourId(harbour);
        
        String boat = json.substring(json.indexOf(":\"") + 2, json.indexOf("\","));
        BoatDTO boatDTO = BOAT_FACADE.getBoat(boat);
        BOAT_FACADE.connectHarbour(boatDTO, harbour_id);
        return null;
    }
}
