package no.kristiania.chat;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDao {

    private final DataSource dataSource;

    public UserDao(DataSource dataSource){
        this.dataSource  = dataSource;
    }

    public void saveUser(User user) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = "insert into users (username, email, tlf_number) values (?, ?, ?)";
            try (var query = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                query.setString(1, user.getUsername());
                query.setString(2, user.getEmailAddress());
                query.setString(3, user.getPhoneNumber());
                query.executeUpdate();

                try (var rs = query.getGeneratedKeys()) {
                    rs.next();
                    user.setId(rs.getInt(1));
                }
            }
        }
    }

    public User retrieveUser(int id) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = "select * from users where id = ?";
            try (var query = connection.prepareStatement(sql)) {
                query.setInt(1, id);

                try (var rs = query.executeQuery()) {
                    rs.next();
                    return createUser(rs);
                }
            }
        }
    }

    private User createUser(ResultSet rs) throws SQLException {
        var user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setEmailAddress(rs.getString("email"));
        user.setPhoneNumber(rs.getString("tlf_number"));
        return user;
    }

    public void updateUser(){
        //To be implemented
    }

}
