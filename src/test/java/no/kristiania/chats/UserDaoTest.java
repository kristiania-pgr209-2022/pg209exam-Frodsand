package no.kristiania.chats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    private UserDao userDao;

    @BeforeEach
    void setUp(){
        var dataSource = H2MemoryDataSource.createH2TestDataSource();
        userDao = new UserDao(dataSource);
    }

    @Test
    public void shouldRetrieveSavedUser() throws SQLException {
        var user = SampleChat.sampleUser();
        userDao.saveUser(user);
        assertThat(userDao.retrieveUser(user.getId()))
                .usingRecursiveComparison()
                .isEqualTo(user)
                .isNotSameAs(user);
    }


}