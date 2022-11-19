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
        var dataSource = H2MemoryDataSource.createH2TestDataSource();
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
                .add("subject", "Test")
                .add("messageBody", "Test post and get message")
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
                .contains("\"messageBody\":\"Test post and get message\"");
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
                .add("subject", "Test")
                .add("messageBody", "Test Message")
                .build();

        JsonObject messageDto = Json.createObjectBuilder()
                .add("senderId", sender.getInt("id"))
                .add("receiverId", receiver.getInt("id"))
                .add("message", message)
                .build();

        postConnection.getOutputStream().write(messageDto.toString().getBytes(StandardCharsets.UTF_8));

        assertThat(postConnection.getResponseCode()).isEqualTo(204);

        var getConnection = createConnection("/api/chat/received/1");

        assertThat(getConnection.getInputStream()).asString(StandardCharsets.UTF_8).contains("\"messageBody\":\"Test Message\"");
    }
    @Test
    public void shouldGetSentMessageByUser() throws IOException{
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
                .add("subject", "Good night")
                .add("messageBody", "Good night!")
                .build();

        JsonObject messageDto = Json.createObjectBuilder()
                .add("senderId", sender.getInt("id"))
                .add("receiverId", receiver.getInt("id"))
                .add("message", message)
                .build();

        postConnection.getOutputStream().write(messageDto.toString().getBytes(StandardCharsets.UTF_8));
        assertThat(postConnection.getResponseCode()).isEqualTo(204);
        var getConnection = createConnection("/api/chat/sent/2");
        assertThat(getConnection.getInputStream()).asString(StandardCharsets.UTF_8).contains("\"messageBody\":\"Good night!\"");
    }

    // In shouldGetSender and shouldGetReceiver we decided to use what is already added to the database at start.
    @Test
    public void shouldGetSender() throws IOException {
        var getConnection = createConnection("/api/chat/sender/3");
        assertThat(getConnection.getResponseCode()).isEqualTo(200);
        assertThat(getConnection.getInputStream()).asString(StandardCharsets.UTF_8).contains("\"emailAddress\":\"Doffen@andeby.com\"");
    }

    @Test
    public void shouldGetReceiver() throws IOException {
        var getConnection = createConnection("/api/chat/receiver/2");
        assertThat(getConnection.getResponseCode()).isEqualTo(200);
        assertThat(getConnection.getInputStream()).asString(StandardCharsets.UTF_8).contains("\"username\":\"Ole\"");
    }

    @Test
    public void shouldPostAndGetUser() throws IOException {
        var postConnection = createConnection("/api/chat/user");
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.setDoOutput(true);

        JsonObject user = Json.createObjectBuilder()
                .add("username", "Kalle Anka")
                .add("emailAddress", "Kalleanka@andeby.com")
                .add("phoneNumber", "12345678")
                .build();

        postConnection.getOutputStream().write(user.toString().getBytes(StandardCharsets.UTF_8));

        assertThat(postConnection.getResponseCode()).isEqualTo(204);

        var getConnection = createConnection("/api/chat/user");
        assertThat(getConnection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("\"username\":\"Kalle Anka\"");
    }


    @Test
    public void shouldUpdateUser() throws IOException {
        var putConnection = createConnection("/api/chat/user");
        putConnection.setRequestMethod("PUT");
        putConnection.setRequestProperty("Content-Type", "application/json");
        putConnection.setDoOutput(true);

        JsonObject updatedUser = Json.createObjectBuilder()
                .add("id", 4)
                .add("username", "Dolly updated")
                .add("emailAddress", "Dolly@andeby.com updated")
                .add("phoneNumber", "12345678 updated")
                .build();

        putConnection.getOutputStream().write(updatedUser.toString().getBytes(StandardCharsets.UTF_8));

        assertThat(putConnection.getResponseCode()).isEqualTo(204);

        var getConnection = createConnection("/api/chat/user");
        assertThat(getConnection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("\"emailAddress\":\"Dolly@andeby.com updated\",\"id\":4,\"phoneNumber\":\"12345678 updated\",\"username\":\"Dolly updated\"");

    }

    private HttpURLConnection createConnection(String path) throws IOException {
        return (HttpURLConnection) new URL(server.getUrl(), path).openConnection();
    }
}
