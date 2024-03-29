package no.kristiania.chat;

import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {

    private final DataSource dataSource;

    @Inject
    public MessageDao(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public int sendMessage(Message message) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = "insert into message (subject, message_body) values (?, ?)";
            try (var query = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                query.setString(1, message.getSubject());
                query.setString(2, message.getMessageBody());
                query.executeUpdate();

                try (var rs = query.getGeneratedKeys()) {
                    rs.next();
                    message.setId(rs.getInt(1));
                }
                return message.getId();
            }
        }
    }

    public Message retrieveMessage(int id) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = "select * from message where id = ?";
            try (var query = connection.prepareStatement(sql)) {
                query.setInt(1, id);
                try (var rs = query.executeQuery()) {
                    if(rs.next()){
                        return createMessage(rs);
                    }
                    else{
                        return null;
                    }
                }
            }
        }
    }

    public void deleteMessage(Message message) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = "delete from message where id = ?";
            try (var query = connection.prepareStatement(sql)) {
                query.setInt(1, message.getId());

                query.executeUpdate();
            }
        }
    }

    public List<Message> getMessageBySubject(String subject) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = "select * from message where subject = ?";
            try (var query = connection.prepareStatement(sql)) {
                query.setString(1, subject);
                try (var rs = query.executeQuery()) {
                    return getMessages(rs);
                }
            }
        }
    }

    public List<Message> showAllMessages() throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = "select * from message";
            try (var query = connection.prepareStatement(sql)) {
                try (var rs = query.executeQuery()) {
                    return getMessages(rs);
                }
            }
        }
    }

    public static Message createMessage(ResultSet rs) throws SQLException {
        var message = new Message();
        message.setId(rs.getInt("id"));
        message.setSubject(rs.getString("subject"));
        message.setMessageBody(rs.getString("message_body"));
        return message;
    }

    private static ArrayList<Message> getMessages(ResultSet rs) throws SQLException {
        var messages = new ArrayList<Message>();
        if (rs != null){
            while(rs.next()){
                messages.add(createMessage(rs));
            }
        }
        return messages;
    }
}
