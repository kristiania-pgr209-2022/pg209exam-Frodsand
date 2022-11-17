package no.kristiania.chat;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatServerTest {

    private ChatServer server;

    @BeforeEach
    void setUp() throws Exception {
        var dataSource = H2MemoryDataSource.createH2TestDataSource();;
        server = new ChatServer(0, dataSource);
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

    // Uses the endpoint to post a message and get all the messages posted. We check if the message sent is there
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

    // Uses the endpoint "/api/chat/messages" to post a message
    // Uses the endpoint "/api/chat/received/{userId} to get a message based on the user. We decided to hard code the id
    @Test
    public void shouldGetMessagesReceivedByUser() throws IOException {
        var postConnection = createConnection("/api/chat/messages");
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.setDoOutput(true);

        JsonObject receiver = Json.createObjectBuilder()
                .add("id", 1)
                .add("username", "Receiver")
                .add("email", "receiver@test.test")
                .add("phoneNumber", "11111111")
                .build();

        JsonObject sender = Json.createObjectBuilder()
                .add("id", 2)
                .add("username", "Sender")
                .add("email", "sender@test.test")
                .add("phoneNumber", "11111111")
                .build();


        JsonObject message = Json.createObjectBuilder()
                .add("subject", "Hello")
                .add("messageBody", "Hello World!")
                .build();

        JsonObject messageDto = Json.createObjectBuilder()
                .add("senderId", sender.getInt("id"))
                .add("receiverId", receiver.getInt("id"))
                .add("message", message)
                .build();

        postConnection.getOutputStream().write(messageDto.toString().getBytes(StandardCharsets.UTF_8));

        assertThat(postConnection.getResponseCode()).isEqualTo(204);

        var getConnection = createConnection("/api/chat/received/1");

        assertThat(getConnection.getInputStream()).asString(StandardCharsets.UTF_8).contains("\"messageBody\":\"Hello World!\"");

    }

    private HttpURLConnection createConnection(String path) throws IOException {
        return (HttpURLConnection) new URL(server.getUrl(), path).openConnection();
    }
}
