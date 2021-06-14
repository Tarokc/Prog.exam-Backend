package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.EMF_Creator;
import utils.Populate;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Path("util")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UtilResource {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Populate POPULATE = new Populate(EMF);

    public UtilResource() {}

    @GET
    @Path("populate")
    public Response populateAll() {
        List<String> populated = POPULATE.populateAll();
        Map<String, List<String>> response = Collections.singletonMap("populated", populated);
        return Response.ok().entity(GSON.toJson(response)).build();
    }

}
