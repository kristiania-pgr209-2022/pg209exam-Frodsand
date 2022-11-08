package no.kristiania.chat;

import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatDao {

    private final DataSource dataSource;

    @Inject
    public ChatDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertIntoChat(User user, Message message) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = "insert into chats (user_id, message_id) values (?, ?)";
            try (var query = connection.prepareStatement(sql)) {
                query.setInt(1, user.getId());
                query.setInt(2, message.getId());

                query.executeUpdate();
            }
        }
    }

    public List<Message> findChatByUser(int userId) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = """
                    select m.*
                    from chats c join messages m on c.message_id = m.id
                    where user_id = ?
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
