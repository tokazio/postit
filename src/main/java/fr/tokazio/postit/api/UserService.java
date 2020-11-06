package fr.tokazio.postit.api;

import javax.websocket.Session;
import java.util.Collection;
import java.util.Set;

public interface UserService {
    void put(String username, Session session);

    Set<String> keySet();

    void remove(String username);

    Collection<Session> values();
}
