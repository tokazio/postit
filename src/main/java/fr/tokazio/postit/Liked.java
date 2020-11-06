package fr.tokazio.postit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.tokazio.postit.api.Liking;

public class Liked {

    private String user;
    private Liking liking;
    private String postitId;

    @JsonCreator
    public Liked(final @JsonProperty("user") String user, final @JsonProperty("liking") Liking liking, final @JsonProperty("postitId") String postitId) {
        this.user = user;
        this.liking = liking;
    }

    public String user() {
        return user;
    }

    public Liking liking() {
        return liking;
    }

    public String postitId() {
        return postitId;
    }
}
