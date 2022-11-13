package no.kristiania.chat;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DbConnector {
    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);
    public static DataSource getDataSource() throws IOException {
        var properties = new Properties();

        try (var fileReader = new FileReader("application.properties")) {
            logger.info("Reading application.properties");
            properties.load(fileReader);
        }

        var dataSource = new SQLServerDataSource();
        dataSource.setURL(properties.getProperty("dataSource.url"));
        dataSource.setUser(properties.getProperty("dataSource.username"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));
        logger.info("Attempting to execute migrations");
        try {
            Flyway.configure().dataSource(dataSource).load().migrate();

        } catch (FlywayException e) {
            logger.info("Failed to execute migration. Error:" + e);
        }
        return dataSource;
    }
}
