package fr.tokazio.postit;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@Path("/api/postit")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostitResource {

    private Set<Postit> items = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public PostitResource() {
        items.add(new Postit("a", "rpetit"));
        items.add(new Postit("b", "bob"));
    }

    @GET
    public Set<Postit> list() {
        return items;
    }

    @Path("/{id}")
    @GET
    public Postit get(@PathParam("id") String id) {
        return items.stream().filter(p -> p.id.equals(id)).findAny().orElse(Postit.NOTFOUND);
    }

    @Path("/{id}")
    @POST
    public Postit edit(@PathParam("id") String id, Postit postit) {
        Postit edited = items.stream().filter(p -> p.id.equals(id)).findAny().orElse(Postit.NOTFOUND);
        if (edited != null) {
            edited.setText(postit.getText());
        }
        return edited;
    }

    @POST
    public Set<Postit> add(Postit postit) {
        items.add(postit);
        return items;
    }

    @Path("/{id}")
    @DELETE
    public Set<Postit> delete(@PathParam("id") String id) {
        items.removeIf(existingFruit -> existingFruit.id.contentEquals(id));
        return items;
    }
}