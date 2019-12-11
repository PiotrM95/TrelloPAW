package server;

import com.mysql.cj.jdbc.CallableStatement;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;

public class ServerConnection {
    Connection connection;

    ///Utworzenie połączenia z serverem
    ServerConnection() throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("micronaut");
        dataSource.setPassword("micronaut");
        dataSource.setDatabaseName("micronaut");
        dataSource.setServerName("www.db4free.net");
        connection = dataSource.getConnection();
    }

    ///Wysłanie zapytania do serwera
    ResultSet query (String query ) throws SQLException
    {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    CallableStatement procedureCall(String query) throws SQLException
    {

        return (CallableStatement) connection.prepareCall(query);
        //stmt.setInt(1, candidateId);
    }

    ResultSet procedureExecute(CallableStatement statement) throws SQLException
    {

        return statement.executeQuery();
    }
}
