package server;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerConnection {
    Connection connection;

    ServerConnection() throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("micronaut");
        dataSource.setPassword("micronaut");
        dataSource.setDatabaseName("micronaut");
        dataSource.setServerName("www.db4free.net");
        connection = dataSource.getConnection();
    }

    ResultSet query (String query ) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }
}
