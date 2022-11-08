package no.kristiania.chats;

import javax.sql.DataSource;
import java.util.List;

public class ChatDao {

    private final DataSource dataSource;

    public ChatDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertIntoChat(User user, Message message){

    }

    public List<Message> findChatByUser(int userId){
        return null;
    }
}
