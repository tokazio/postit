package fr.tokazio.postit.api;

import fr.tokazio.postit.Postit;
import fr.tokazio.postit.User;
import fr.tokazio.postit.exceptions.CantLikeAPostitIOwnException;
import fr.tokazio.postit.exceptions.NoUserException;
import fr.tokazio.postit.exceptions.PostitNotFoundException;

import java.util.List;

public interface PostitService {
    Postit get(String id) throws PostitNotFoundException;

    List<Postit> all();

    Postit edit(String id, Postit postit) throws PostitNotFoundException;

    Postit add(Postit postit);

    Postit delete(String id) throws PostitNotFoundException;

    boolean save();

    boolean load();

    Liking like(String id, User user) throws PostitNotFoundException, CantLikeAPostitIOwnException, NoUserException;

    void clear();
}
