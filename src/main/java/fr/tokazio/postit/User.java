package fr.tokazio.postit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class User {

    private @NotEmpty
    @NotNull
    final String user;

    @JsonCreator
    public User(final @JsonProperty("user") String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }


}
