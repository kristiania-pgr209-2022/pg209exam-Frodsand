package no.kristiania.chat;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.sql.SQLException;
import java.util.List;

@Path("/chat")
public class ChatEndpoint {

    @Inject
    private MessageDao messageDao;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message>getChatByUser() throws SQLException {
        return messageDao.showAllMessages();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendMessage(Message message) throws SQLException {
        messageDao.sendMessage(message);
    }
}