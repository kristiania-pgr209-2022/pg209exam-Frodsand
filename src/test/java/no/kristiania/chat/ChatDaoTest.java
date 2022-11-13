package no.kristiania.chat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class ChatDaoTest {

    private ChatDao chatDao;

    private UserDao userDao;

    private MessageDao messageDao;

    @BeforeEach
    void setUp(){
        var dataSource = H2MemoryDataSource.createH2TestDataSource();
        chatDao = new ChatDao(dataSource);
        userDao = new UserDao(dataSource);
        messageDao = new MessageDao(dataSource);
    }

    @Test
    public void shouldShowMessagesByUser() throws SQLException {
        var message1 = SampleChat.sampleMessage();
        var message2 = SampleChat.sampleMessage();
        messageDao.sendMessage(message1);
        messageDao.sendMessage(message2);

        var user1 = SampleChat.sampleUser();
        var user2 = SampleChat.sampleUser();
        userDao.saveUser(user1);
        userDao.saveUser(user2);

        chatDao.insertIntoChat(user1, message1);
        chatDao.insertIntoChat(user2, message2);

        assertThat(chatDao.findChatByReceiver(user1.getId()))
                .extracting(Message::getId)
                .contains(message1.getId());

        assertThat(chatDao.findChatByReceiver(user2.getId()))
                .extracting(Message::getId)
                .contains(message2.getId());


    }

}