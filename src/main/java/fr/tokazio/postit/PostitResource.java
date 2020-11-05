package fr.tokazio.postit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Path("/api/postit")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PostitResource {

    public static final String LOAD_JSON = "saved.json";
    public static final String SAVE_JSON = "saved.json";

    private Set<Postit> items = new HashSet<>();//Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));
    @Inject
    private UserList userList;

    public PostitResource() {
        load();
    }

    @Path("/categories")
    @GET
    public List<Categorie> listCategories() {
        return Categories.ALL;
    }

    @Path("/users")
    @GET
    public Set<String> listUsers() {
        return userList.keySet();
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
        if (!"system".equals(edited.getUser())) {
            edited.setText(postit.getText());
            edited.setCategorie(postit.getCategorie());
        }
        save();
        return edited;
    }

    @Path("/save")
    @POST
    public boolean userSave() {
        save();
        return true;
    }

    @Path("/like/{id}")
    @POST
    public String like(@PathParam("id") String id, User user) {
        String out = "";
        Postit edited = items.stream().filter(p -> p.id.equals(id)).findAny().orElse(Postit.NOTFOUND);
        if (!"system".equals(edited.getUser())) {
            if (edited.likedBy(user.getUser())) {
                out = "Vous aimez";
            } else {
                out = "Vous n'aimez plus";
            }
        }
        save();
        return out;
    }

    @POST
    public Set<Postit> add(Postit postit) {
        items.add(postit);
        save();
        return items;
    }

    @Path("/{id}")
    @DELETE
    public Set<Postit> delete(@PathParam("id") String id) {
        items.removeIf(existingFruit -> existingFruit.id.contentEquals(id));
        save();
        return items;
    }

    private void save() {
        try {
            File f = new File(SAVE_JSON);
            if (!f.exists()) {
                f.createNewFile();
            }
            new ObjectMapper().writeValue(f, items);
            System.out.println("saved to " + f.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        try {
            File f = new File(LOAD_JSON);
            if (f.exists()) {
                ObjectMapper mapper = new ObjectMapper();

                items = mapper.readValue(f, new TypeReference<HashSet<Postit>>() {
                });
                System.out.println("loaded " + items.size() + " from " + f.getAbsolutePath());
                System.out.println(items);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}