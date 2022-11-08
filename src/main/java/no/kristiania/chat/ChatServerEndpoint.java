package no.kristiania.chat;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.sql.SQLException;
import java.util.List;

@Path("/chat")
public class ChatServerEndpoint {

    @Inject
    private ChatDao chatDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message>getChatByUser(int userId) throws SQLException {
        return chatDao.findChatByUser(userId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendMessage(User user, Message message) throws SQLException {
       chatDao.insertIntoChat(user, message);
    }
}
