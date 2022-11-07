package no.kristiania.chats;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.sql.DataSource;
import java.net.URL;

public class ChatServer {

    private Server server;

    public ChatServer(int port, DataSource dataSource){
        this.server = new Server(port);

        server.setHandler(new HandlerList(
                createApp(dataSource),
                createAppContext()
        ));

    }

    private ServletContextHandler createApp(DataSource dataSource) {
        var contextHandler = new ServletContextHandler(server, "/api");
        contextHandler.addServlet(new ServletHolder(new ServletContainer(
                new ChatServerConfig(dataSource)
        )), "/*");

        return contextHandler;
    }

    private WebAppContext createAppContext() {
        return null;
    }

    public URL getUrl() {
        return null;
    }
    private void start() throws Exception {
        server.start();
    }

    public static void main(String[] args) throws Exception {
        ChatServer server = new ChatServer(8080, DbConnector.getDataSource());
        server.start();
    }

}
