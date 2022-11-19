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
    public void shouldShowMessagesReceivedByUser() throws SQLException {
        var message1 = SampleChat.sampleMessage();
        var message2 = SampleChat.sampleMessage();
        messageDao.sendMessage(message1);
        messageDao.sendMessage(message2);

        var user1 = SampleChat.sampleUser();
        var user2 = SampleChat.sampleUser();
        userDao.saveUser(user1);
        userDao.saveUser(user2);

        chatDao.insertIntoChat(user1.getId(), user2.getId(), message1.getId());
        chatDao.insertIntoChat(user2.getId(), user1.getId(), message2.getId());

        assertThat(chatDao.findChatByReceiver(user1.getId()))
                .extracting(Message::getId)
                .contains(message2.getId());

        assertThat(chatDao.findChatByReceiver(user2.getId()))
                .extracting(Message::getId)
                .contains(message1.getId());
    }

    @Test
    public void shouldShowMessagesSentByUser() throws SQLException {
        var message1 = SampleChat.sampleMessage();
        var message2 = SampleChat.sampleMessage();
        messageDao.sendMessage(message1);
        messageDao.sendMessage(message2);

        var user1 = SampleChat.sampleUser();
        var user2 = SampleChat.sampleUser();
        userDao.saveUser(user1);
        userDao.saveUser(user2);

        chatDao.insertIntoChat(user1.getId(), user2.getId(), message1.getId());
        chatDao.insertIntoChat(user2.getId(), user1.getId(), message2.getId());

        assertThat(chatDao.findChatBySender(user1.getId()))
                .extracting(Message::getId)
                .contains(message1.getId());

        assertThat(chatDao.findChatBySender(user2.getId()))
                .extracting(Message::getId)
                .contains(message2.getId());
    }

    @Test
    public void shouldReturnChatBetweenTwoUsers() throws SQLException {
        var message1 = SampleChat.sampleMessage();
        var message2 = SampleChat.sampleMessage();
        messageDao.sendMessage(message1);
        messageDao.sendMessage(message2);

        var user1 = SampleChat.sampleUser();
        var user2 = SampleChat.sampleUser();
        userDao.saveUser(user1);
        userDao.saveUser(user2);

        chatDao.insertIntoChat(user1.getId(), user2.getId(), message1.getId());
        chatDao.insertIntoChat(user2.getId(), user1.getId(), message2.getId());

        assertThat(chatDao.getChatBetweenTwoUsers(user1.getId(), user2.getId()))
                .extracting(Message::getId)
                .contains(message2.getId());

        assertThat(chatDao.getChatBetweenTwoUsers(user2.getId(), user1.getId()))
                .extracting(Message::getId)
                .contains(message1.getId());
    }

    @Test
    public void shouldReturnSenderOfMessage() throws SQLException {
        var message = SampleChat.sampleMessage();

        messageDao.sendMessage(message);

        var sender = SampleChat.sampleUser();
        var receiver = SampleChat.sampleUser();

        userDao.saveUser(sender);
        userDao.saveUser(receiver);

        chatDao.insertIntoChat(sender.getId(), receiver.getId(), message.getId());

        assertThat(chatDao.retrieveSender(message.getId()))
                .usingRecursiveComparison()
                .isEqualTo(sender)
                .isNotSameAs(sender);
    }

    @Test
    public void shouldReturnReceiverOfMessage() throws SQLException {
        var message = SampleChat.sampleMessage();

        messageDao.sendMessage(message);

        var sender = SampleChat.sampleUser();
        var receiver = SampleChat.sampleUser();

        userDao.saveUser(sender);
        userDao.saveUser(receiver);

        chatDao.insertIntoChat(sender.getId(), receiver.getId(), message.getId());

        assertThat(chatDao.retrieveReceiver(message.getId()))
                .usingRecursiveComparison()
                .isEqualTo(receiver)
                .isNotSameAs(receiver);
    }
}