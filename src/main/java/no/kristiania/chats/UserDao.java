package no.kristiania.chats;

import javax.sql.DataSource;

public class UserDao {

    private final DataSource dataSource;

    public UserDao(DataSource dataSource){
        this.dataSource  = dataSource;
    }

    public void saveUser(User user){

    }

    public User retrieveUser(int id){
        return null;
    }

    public void updateUser(){

    }

}
