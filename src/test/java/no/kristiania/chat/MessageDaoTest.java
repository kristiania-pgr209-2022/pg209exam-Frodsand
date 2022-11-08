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
}