package fr.tokazio.postit;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

@Path("/api/postit")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PostitResource {

    private @Inject
    PostitService service;
    private @Inject
    UserService userService;

    @Path("/categories")
    @GET
    public List<Category> listCategories() {
        return Categories.ALL;
    }

    @Path("/users")
    @GET
    public Set<String> listUsers() {
        return userService.keySet();
    }

    @GET
    public List<Postit> list() {
        return service.all();
    }

    @Path("/{id}")
    @GET
    public Postit get(final @PathParam("id") String id) throws PostitNotFoundException {
        return service.get(id);
    }

    @Path("/{id}")
    @POST
    public Postit edit(final @PathParam("id") String id, final Postit postit) throws PostitNotFoundException {
        return service.edit(id, postit);
    }

    @Path("/save")
    @POST
    public boolean userSave() {
        return service.save();
    }

    @Path("/like/{id}")
    @POST
    public Liking like(final @PathParam("id") String id, final User user) throws PostitNotFoundException, CantLikeAPostitIOwnException {
        return service.like(id, user);
    }

    @POST
    public Postit add(final Postit postit) {
        return service.add(postit);
    }

    @Path("/{id}")
    @DELETE
    public Postit delete(final @PathParam("id") String id) throws PostitNotFoundException {
        return service.delete(id);
    }


}