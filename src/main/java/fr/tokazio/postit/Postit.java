package fr.tokazio.postit;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.UUID;

public class Postit {

    public static final Postit NOTFOUND = new Postit("PostIt introuvable", "system");
    String id = UUID.randomUUID().toString();
    String text;
    String user;

    @JsonCreator
    public Postit(String text, String user) {
        this.text = text;
        this.user = user;
    }

    public String getText() {
        return text != null ? text : "";
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
