package no.kristiania.chat;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatServerTest {

    private ChatServer server;

    @BeforeEach
    void setUp() throws Exception {
        server = new ChatServer(0, H2MemoryDataSource.createH2TestDataSource());
        server.start();
    }

    @Test
    public void shouldShowTitleForFrontPage() throws IOException {
        var connection = createConnection("/");
        assertThat(connection.getResponseCode()).isEqualTo(200);
        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("<title>Chat</title>");
    }

    @Test
    public void shouldPostAndGetMessage() throws IOException {
        var postConnection = createConnection("/api/chat/messages");
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.setDoOutput(true);

        JsonObject message = Json.createObjectBuilder()
                .add("subject", "Hello")
                .add("messageBody", "Hello World!")
                .build();

        JsonObject messageDto = Json.createObjectBuilder()
                .add("senderId", 1)
                .add("receiverId", 2)
                .add("message", message)
                .build();

        postConnection.getOutputStream().write(messageDto.toString().getBytes(StandardCharsets.UTF_8));

        assertThat(postConnection.getResponseCode()).isEqualTo(204);

        var getConnection = createConnection("/api/chat/messages");
        assertThat(getConnection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("\"messageBody\":\"Hello World!\"");
    }

    private HttpURLConnection createConnection(String path) throws IOException {
        return (HttpURLConnection) new URL(server.getUrl(), path).openConnection();
    }
}
