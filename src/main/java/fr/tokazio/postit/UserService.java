package fr.tokazio.postit;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class UserService {

    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    public void put(final String username, final Session session) {
        sessions.put(username, session);
    }

    public Set<String> keySet() {
        return sessions.keySet();
    }

    public void remove(final String username) {
        sessions.remove(username);
    }

    public Collection<Session> values() {
        return sessions.values();
    }
}
