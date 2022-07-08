package fr.tokazio.postit.infra;

import fr.tokazio.postit.Category;
import fr.tokazio.postit.api.CategoryService;
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
import java.util.List;

import static fr.tokazio.postit.api.Version.V1;

@Path("/api/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class CategoryResource {

    @Inject
    CategoryService service;

    @GET
    @Path("/")
    @Counted(name = "categories.list.count", description = "Count GET /categories")
    @Timed(name = "categories.list.time", description = "Time GET /categories", unit = MetricUnits.MILLISECONDS)
    @Consumes(V1)
    public List<Category> list() {
        return service.all();
    }

}
