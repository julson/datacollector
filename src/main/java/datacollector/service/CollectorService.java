package datacollector.service;

import datacollector.model.Transaction;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * @author Julson Lim (julsonlim@gmail.com)
 */
@Path("/collector/v1")
public interface CollectorService {

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    void save(Transaction transaction);

    @GET
    @Path("ping")
    String ping();
}
