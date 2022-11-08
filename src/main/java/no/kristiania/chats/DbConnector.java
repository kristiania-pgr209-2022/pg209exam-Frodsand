package no.kristiania.chats;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DbConnector {
    public static DataSource getDataSource() throws IOException {
        var properties = new Properties();

        try (var fileReader = new FileReader("application.properties")) {
            properties.load(fileReader);
        }

        var dataSource = new SQLServerDataSource();
        dataSource.setURL(properties.getProperty("dataSource.url"));
        dataSource.setUser(properties.getProperty("dataSource.username"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }
}
