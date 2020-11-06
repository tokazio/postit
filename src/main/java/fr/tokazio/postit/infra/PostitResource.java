package fr.tokazio.postit.infra;

import fr.tokazio.postit.Postit;
import fr.tokazio.postit.User;
import fr.tokazio.postit.api.Liking;
import fr.tokazio.postit.api.PostitService;
import fr.tokazio.postit.api.ResponseCode;
import fr.tokazio.postit.api.ResponseStatus;
import fr.tokazio.postit.exceptions.CantLikeAPostitIOwnException;
import fr.tokazio.postit.exceptions.NoUserException;
import fr.tokazio.postit.exceptions.PostitNotFoundException;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static fr.tokazio.postit.api.Version.V1;

@Path("/api/postits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PostitResource {

    @Inject
    private PostitService service;

    @GET
    @Counted(name = "posits.list.count", description = "Count GET /postit")
    @Timed(name = "posits.list.time", description = "Time GET /postit", unit = MetricUnits.MILLISECONDS)
    @Consumes(V1)
    public List<Postit> list() {
        return service.all();
    }

    @GET
    @Path("/{id}")
    @Counted(name = "posits.get.count", description = "Count GET /postit/{id}")
    @Timed(name = "posits.get.time", description = "Time GET /postit/{id}", unit = MetricUnits.MILLISECONDS)
    @Consumes(V1)
    public Postit get(final @PathParam("id") String id) throws PostitNotFoundException {
        return service.get(id);
    }

    @POST
    @Path("/{id}")
    @Counted(name = "posits.edit.count", description = "Count POST /postit/{id}")
    @Timed(name = "posits.edit.time", description = "Time POST /postit/{id}", unit = MetricUnits.MILLISECONDS)
    @Consumes(V1)
    public Postit edit(final @PathParam("id") String id, final Postit postit) throws PostitNotFoundException {
        return service.edit(id, postit);
    }

    @POST
    @Path("/save")
    @ResponseStatus(ResponseCode.ACCEPTED)
    @Counted(name = "posits.save.count", description = "Count POST /postit/save")
    @Timed(name = "posits.save.time", description = "Time POST /postit/save", unit = MetricUnits.MILLISECONDS)
    @Consumes(V1)
    public boolean userSave() {
        return service.save();
    }

    @POST
    @Path("/like/{id}")
    @ResponseStatus(ResponseCode.ACCEPTED)
    @Counted(name = "posits.like.count", description = "Count POST /postit/like/{id}")
    @Timed(name = "posits.like.time", description = "Time POST /postit/lile/{id}", unit = MetricUnits.MILLISECONDS)
    @Consumes(V1)
    public Liking like(final @PathParam("id") String id, final User user) throws PostitNotFoundException, CantLikeAPostitIOwnException, NoUserException {
        return service.like(id, user);
    }

    @POST
    @ResponseStatus(ResponseCode.CREATED)
    @Counted(name = "posits.add.count", description = "Count POST /postit")
    @Timed(name = "posits.add.time", description = "Time POST /postit", unit = MetricUnits.MILLISECONDS)
    @Consumes(V1)
    public Postit add(final Postit postit) {
        return service.add(postit);
    }

    @DELETE
    @Path("/{id}")
    @Counted(name = "posits.delete.count", description = "Count DELETE /postit/{id}")
    @Timed(name = "posits.delete.time", description = "Time DELETE /postit/{id}", unit = MetricUnits.MILLISECONDS)
    @Consumes(V1)
    public Postit delete(final @PathParam("id") String id) throws PostitNotFoundException {
        return service.delete(id);
    }

}