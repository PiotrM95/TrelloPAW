package server;
        import io.micronaut.runtime.Micronaut;
        import java.sql.ResultSet;
        import java.sql.SQLException;

public class Application {

    public static void main(String[] args) throws SQLException {
        Micronaut.run(Application.class);
        ServerConnection connection = new ServerConnection();
        ResultSet rs = connection.query ("SELECT * FROM test");


        System.out.println(rs.getMetaData().getColumnName(1) + " " + rs.getMetaData().getColumnName(2));
        rs.next();
        System.out.println(rs.getInt("id") + " " + rs.getString("firstname"));
        rs.close();
    }
}