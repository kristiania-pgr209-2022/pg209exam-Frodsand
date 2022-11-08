package no.kristiania.chat;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class H2MemoryDataSource {

    public static DataSource createH2TestDataSource(){
        var dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:testDataBase;DB_CLOSE_DELAY=-1;MODE=LEGACY");
        var flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
        return dataSource;
    }
}
