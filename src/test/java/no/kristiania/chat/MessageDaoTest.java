package no.kristiania.chat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class MessageDaoTest {

    private MessageDao messageDao;

    @BeforeEach
    void setUp(){
        var dataSource = H2MemoryDataSource.createH2TestDataSource();
        messageDao = new MessageDao(dataSource);
    }

    @Test
    public void shouldReturnSavedMessage() throws SQLException {
        var message = SampleChat.sampleMessage();
        messageDao.sendMessage(message);
        assertThat(messageDao.retrieveMessage(message.getId()))
                .usingRecursiveComparison()
                .isEqualTo(message)
                .isNotSameAs(message);
    }

    @Test
    public void shouldDeleteMessage() throws SQLException {
        var message = SampleChat.sampleMessage();
        messageDao.sendMessage(message);

        assertThat(messageDao.retrieveMessage(message.getId()))
                .usingRecursiveComparison()
                .isEqualTo(message)
                .isNotSameAs(message);

        messageDao.deleteMessage(message);

        assertThat(messageDao.retrieveMessage(message.getId()))
                .usingRecursiveComparison()
                .isNull();
    }

    @Test
    public void shouldReturnMessageBySubject() throws SQLException {
        var message = SampleChat.sampleMessage();
        messageDao.sendMessage(message);

        assertThat(messageDao.getMessageBySubject(message.getSubject()))
                .extracting(Message::getId)
                .contains(message.getId());
    }

    @Test
    public void shouldShowAllMessages() throws SQLException {
        var m1 = SampleChat.sampleMessage();
        var m2 = SampleChat.sampleMessage();
        var m3 = SampleChat.sampleMessage();

        messageDao.sendMessage(m1);
        messageDao.sendMessage(m2);
        messageDao.sendMessage(m3);

        assertThat(messageDao.showAllMessages())
                .extracting(Message::getId)
                .contains(m1.getId(), m2.getId(), m3.getId());
    }
}