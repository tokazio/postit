package fr.tokazio.postit;

import fr.tokazio.postit.api.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    @Override
    public void put(final String username, final Session session) {
        sessions.put(username, session);
    }

    @Override
    public Set<String> keySet() {
        return sessions.keySet();
    }

    @Override
    public void remove(final String username) {
        sessions.remove(username);
    }

    @Override
    public Collection<Session> values() {
        return sessions.values();
    }
}
