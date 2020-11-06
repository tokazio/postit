package fr.tokazio.postit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotEmpty;

public class User {

    @NotEmpty
    @Nonnull
    private final String user;

    @JsonCreator
    public User(final @JsonProperty("user") String user) {
        this.user = user != null ? user : "";
    }

    @Nonnull
    public String getUser() {
        return user;
    }


}
