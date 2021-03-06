package fr.tokazio.postit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Postit {

    private static final Logger LOGGER = LoggerFactory.getLogger(Postit.class);

    @Nonnull
    String id = UUID.randomUUID().toString();
    private final List<String> likedBy = new ArrayList<>();
    private @NotEmpty @NotNull String text;
    private @NotEmpty @NotNull String user;
    private @NotNull Category category;

    @JsonCreator
    public Postit(final @JsonProperty("text") String text, final @JsonProperty("user") String user, final @JsonProperty("category") Category category) {
        this.text = text;
        this.user = user;
        this.category = category;
    }

    public Postit() {
        //default constructor used by Jackson to load from file
    }

    public List<String> getLikedBy() {
        return likedBy;
    }

    public String getText() {
        return text != null ? text : "";
    }

    public void setText(final String text) {
        this.text = text;
    }

    @Nonnull
    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public Category getCategory() {
        return category;
    }

    public Postit setCategorie(final Category category) {
        this.category = category;
        return this;
    }

    public boolean likedBy(final String user) {
        if (likedBy.contains(user)) {
            LOGGER.info(id + " UnLiked by " + user);
            likedBy.remove(user);
            return false;
        }
        LOGGER.info(id + " Liked by " + user);
        likedBy.add(user);
        return true;

    }

    @Override
    public String toString() {
        return "Postit{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", user='" + user + '\'' +
                ", category=" + category +
                ", likedBy=" + likedBy +
                '}';
    }
}
