package fr.tokazio.postit;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.websocket.*;
import java.net.URI;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class PostitWebSockTestIntegration {

    private static final LinkedBlockingDeque<String> MESSAGES = new LinkedBlockingDeque<>();

    @TestHTTPResource("/websock/stu")
    URI uri;

    @Test
    public void testWebsocket() throws Exception {
        try (Session session = ContainerProvider.getWebSocketContainer().connectToServer(Client.class, uri)) {
            assertThat(msg()).isEqualTo("CONNECT");
            assertThat(msg()).startsWith("joined::").contains("stu");
        }
    }

    //==========================================
    //Utilities
    //==========================================

    private String msg() throws InterruptedException {
        return MESSAGES.poll(10, TimeUnit.SECONDS);
    }

    @ClientEndpoint
    public static class Client {

        @OnOpen
        public void open(Session session) {
            MESSAGES.add("CONNECT");
            // Send a message to indicate that we are ready,
            // as the message handler may not be registered immediately after this callback.
            session.getAsyncRemote().sendText("_ready_");
        }

        @OnMessage
        void message(String msg) {
            MESSAGES.add(msg);
        }

    }
}
