package no.kristiania.chat;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.sql.DataSource;

public class ChatServerConfig extends ResourceConfig {
    public ChatServerConfig(DataSource dataSource) {
        super(ChatServerEndpoint.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(dataSource).to(DataSource.class);
                bind(MessageDao.class).to(MessageDao.class);
            }
        });
    }
}
