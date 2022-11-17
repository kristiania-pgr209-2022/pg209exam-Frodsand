package no.kristiania.chat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    public void shouldUpdateUserInformation() throws SQLException {
        var user = new User();

        user.setUsername("John");
        user.setEmailAddress("john@doe.com");
        user.setPhoneNumber("11111111");

        userDao.saveUser(user);

        assertThat(userDao.retrieveUser(user.getId()))
                .usingRecursiveComparison()
                .isEqualTo(user)
                .isNotSameAs(user);

        userDao.updateUser(user.getId(), "Jane", "jane@doe.com", "22222222");

        assertThat(userDao.retrieveUser(user.getId()))
                .usingRecursiveComparison()
                .isNotEqualTo(user);
    }
}