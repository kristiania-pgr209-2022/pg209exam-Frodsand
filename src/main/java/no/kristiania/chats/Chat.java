package no.kristiania.chats;

public class Chat {

    private int id;

    private Message message;

    private User user;

    public Chat(Message message, User user){
        this.message = message;
        this.user = user;
    }
}
