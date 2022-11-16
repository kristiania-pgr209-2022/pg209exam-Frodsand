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
        var user = SampleChat.sampleUser();
        userDao.saveUser(user);

        assertThat(userDao.retrieveUser(user.getId()))
                .usingRecursiveComparison()
                        .isEqualTo(user)
                                .isNotSameAs(user);

        var updatedUser = userDao.updateUser(user.getId());

        assertThat(userDao.retrieveUser(user.getId()))
                .usingRecursiveComparison()
                .isNotEqualTo(updatedUser);


    }


}