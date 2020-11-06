package fr.tokazio.postit.infra;

import fr.tokazio.postit.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websock/{username}")
@ApplicationScoped
public class PostitWebSock {

    public static final String JOINED = "joined";
    public static final String CMD_SEPARATOR = "::";
    public static final String LEFT = "left";
    public static final String MESSAGE = "message";

    private static final Logger LOGGER = LoggerFactory.getLogger(PostitWebSock.class);

    @Inject
    private UserService userService;

    @OnOpen
    public void onOpen(final Session session, final @PathParam("username") String username) {
        userService.put(username, session);
        broadcast(JOINED + CMD_SEPARATOR + "L'utilisateur " + username + " nous à rejoint");
    }

    @OnClose
    public void onClose(final Session session, final @PathParam("username") String username) {
        userService.remove(username);
        broadcast(LEFT + CMD_SEPARATOR + "L'utilisateur " + username + " nous à quitté");
    }

    @OnError
    public void onError(final Session session, final @PathParam("username") String username, final Throwable throwable) {
        userService.remove(username);
        broadcast(LEFT + CMD_SEPARATOR + "L'utilisateur " + username + " à eu un problème: " + throwable);
    }

    @OnMessage
    public void onMessage(final String message, final @PathParam("username") String username) {
        broadcast(MESSAGE + CMD_SEPARATOR + username + ": " + message);
    }

    private void broadcast(final String message) {
        userService.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result -> {
                if (result.getException() != null) {
                    LOGGER.error("Unable to send message: " + result.getException());
                }
            });
        });
    }

}