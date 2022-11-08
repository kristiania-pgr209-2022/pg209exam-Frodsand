package no.kristiania.chats;



import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ChatServer {

    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);

    private Server server;
    public ChatServer(int port, DataSource dataSource) throws IOException {
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

    private WebAppContext createAppContext() throws IOException {
        WebAppContext context = new WebAppContext();
        context.setContextPath("/");

        Resource resource = Resource.newClassPathResource("/webapp");
        File source = getSource(resource);
        if(source != null){
            context.setBaseResource(Resource.newResource(source));
            context.setInitParameter(DefaultServlet.CONTEXT_INIT + "useFileMappedBuffer", "false");
        } else {
            context.setBaseResource(resource);
        }
        return context;
    }

    private File getSource(Resource resource) throws IOException {
        if (resource.getFile() == null){
            return null;
        }
        File source = new File(resource.getFile().getAbsolutePath()
                .replace('\\', '/')
                .replace("target/classes", "src/main/resource"));
        return source.exists() ? source : null;
    }

    public URL getUrl() throws MalformedURLException {
        return server.getURI().toURL();
    }
    private void start() throws Exception {
        server.start();
        logger.info("Server is running on {} ", getUrl());
    }

    public static void main(String[] args) throws Exception {
        ChatServer server = new ChatServer(8080, DbConnector.getDataSource());
        server.start();
    }

}
