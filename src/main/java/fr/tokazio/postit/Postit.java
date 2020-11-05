package fr.tokazio.postit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Postit {

    public static final Postit NOTFOUND = new Postit("PostIt introuvable", "system", Categories.NONE);

    String id = UUID.randomUUID().toString();
    String text;
    String user;
    Categorie categorie;
    List<String> likedBy = new ArrayList<>();

    @JsonCreator
    public Postit(@JsonProperty("text") String text, @JsonProperty("user") String user, @JsonProperty("categorie") Categorie categorie) {
        this.text = text;
        this.user = user;
        this.categorie = Categories.from(categorie);
    }

    public Postit() {

    }

    public List<String> getLikedBy() {
        return likedBy;
    }

    public Postit setLikedBy(List<String> likedBy) {
        this.likedBy = likedBy;
        return this;
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

    public Categorie getCategorie() {
        return categorie;
    }

    public Postit setCategorie(Categorie categorie) {
        this.categorie = categorie;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Postit{");
        sb.append("id='").append(id).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append(", user='").append(user).append('\'');
        sb.append(", categorie=").append(categorie);
        sb.append('}');
        return sb.toString();
    }

    public boolean likedBy(String user) {
        if (likedBy.contains(user)) {
            System.out.println("UnLiked by " + user);
            likedBy.remove(user);
            return false;
        }
        System.out.println("Liked by " + user);
        likedBy.add(user);
        return true;

    }
}
