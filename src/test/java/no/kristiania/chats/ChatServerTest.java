package no.kristiania.chats;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatServerTest {

    private ChatServer server;

    @Test
    public void shouldShowTitleForFrontPage() throws IOException {
        var connection = createConnection("/");
        assertThat(connection.getResponseCode()).isEqualTo(200);
        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("<title>Chat</title>");

    }

    private HttpURLConnection createConnection(String path) throws IOException {
        return (HttpURLConnection) new URL(server.getUrl(), path).openConnection();
    }
}
