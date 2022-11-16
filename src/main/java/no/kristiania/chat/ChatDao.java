package no.kristiania.chat;

import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatDao {

    private Logger logger = LoggerFactory.getLogger(ChatDao.class);

    private final DataSource dataSource;

    @Inject
    public ChatDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertIntoChat(int senderId, int receiverId, int messageId) throws SQLException {
        logger.info(String.valueOf(senderId));
        try (var connection = dataSource.getConnection()) {
            var sql = "insert into chat (sender_id, receiver_id, message_id) values (?, ?, ?)";
            try (var query = connection.prepareStatement(sql)) {
                query.setInt(1, senderId);
                query.setInt(2, receiverId);
                query.setInt(3, messageId);

                query.executeUpdate();
            }
        }
    }

    public List<Message> findChatByReceiver(int userId) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = """
                    select m.*
                    from chat c join message m on c.message_id = m.id
                    where receiver_id = ?
                    """;
            try (var query = connection.prepareStatement(sql)) {
                query.setInt(1, userId);

                ArrayList<Message> messages;
                try (var rs = query.executeQuery()) {
                    messages = new ArrayList<>();
                    while (rs.next()) {
                        messages.add(MessageDao.createMessage(rs));
                    }
                    return messages;
                }

            }
        }
    }

    public User retrieveSender(int receiverId) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = """
                      select u 
                      from chat c join user u on c.sender_id = u.id
                      where receiver = id = ?
                      """;
            try (var query = connection.prepareStatement(sql)) {
                query.setInt(1, receiverId);

                try (var rs = query.executeQuery()) {
                    return UserDao.createUser(rs);
                }
            }
        }
    }


    public List<Message> findChatBySender(int userId) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = """
                    select m.*
                    from chat c join message m on c.message_id = m.id
                    where sender_id = ?
                    """;
            try (var query = connection.prepareStatement(sql)) {
                query.setInt(1, userId);

                ArrayList<Message> messages;
                try (var rs = query.executeQuery()) {
                    messages = new ArrayList<>();
                    while (rs.next()) {
                        messages.add(MessageDao.createMessage(rs));
                    }
                    return messages;
                }

            }
        }
    }
}
