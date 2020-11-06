package fr.tokazio.postit.infra;

import fr.tokazio.postit.api.UserService;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Set;

import static fr.tokazio.postit.api.Version.V1;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UserResource {

    @Inject
    private UserService service;

    @GET
    @Path("/")
    @Counted(name = "users.list.count", description = "Count GET /users")
    @Timed(name = "users.list.time", description = "Time GET /users", unit = MetricUnits.MILLISECONDS)
    @Consumes(V1)
    public Set<String> list() {
        return service.keySet();
    }

}
