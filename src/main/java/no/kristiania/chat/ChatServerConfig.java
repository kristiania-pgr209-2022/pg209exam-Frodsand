package no.kristiania.chat;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.sql.DataSource;

public class ChatServerConfig extends ResourceConfig {
    public ChatServerConfig(final DataSource dataSource) {
        super(ChatEndpoint.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(dataSource).to(DataSource.class);
                bind(MessageDao.class).to(MessageDao.class);
                bind(ChatDao.class).to(ChatDao.class);
                bind(UserDao.class).to(UserDao.class);
            }
        });
    }
}
