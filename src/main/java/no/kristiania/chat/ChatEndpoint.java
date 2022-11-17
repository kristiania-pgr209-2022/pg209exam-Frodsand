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

    @Path("/received/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message>getChatByReceiver(@PathParam("userId") int userId) throws SQLException {
        return chatDao.findChatByReceiver(userId);
    }

    @Path("/sent/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message>getChatBySender(@PathParam("userId") int userId) throws SQLException {
        return chatDao.findChatBySender(userId);
    }

    @Path("/sender/{messageId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getSender(@PathParam("messageId") int messageId) throws SQLException {
        return chatDao.retrieveSender(messageId);
    }

    @Path("/receiver/{messageId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getReceiver(@PathParam("messageId") int messageId) throws SQLException {
        return chatDao.retrieveReceiver(messageId);
    }


    @Path("/messages")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void sendMessage(MessageDto message) throws SQLException {
        messageDao.sendMessage(message.getMessage());
        chatDao.insertIntoChat(message.getSenderId(), message.getReceiverId(), message.getMessage().getId());
    }

    @Path("/messages")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateMessage(){

    }

    @Path("/messages")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> getAllMessages() throws SQLException {
        return messageDao.showAllMessages();
    }

    @Path("/user")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() throws SQLException {
        return userDao.showAllUsers();
    }
}