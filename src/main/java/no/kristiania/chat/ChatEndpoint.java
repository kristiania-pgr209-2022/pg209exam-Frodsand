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
    @Inject
    private UserDao userDao;

    @Inject
    private ChatDao chatDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message>getChatBySender() throws SQLException {
        return chatDao.findChatBySender(1);
    }

    @Path("/messages")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message>getChatByUser() throws SQLException {
        return messageDao.showAllMessages();
    }

    @Path("/messages")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendMessage(Message message) throws SQLException {
        messageDao.sendMessage(message);
    }

    @Path("/user")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() throws SQLException {
        return userDao.showAllUsers();
    }
}