package server;
        import com.fasterxml.jackson.core.JsonProcessingException;
        import io.micronaut.runtime.Micronaut;

        import java.sql.SQLException;

public class Application {

    public static void main(String[] args) throws SQLException, JsonProcessingException {
        Micronaut.run(Application.class);
        ///Ponizej do JSON'a
        /*System.out.println(ConverterJSON.tableListToJSON(dbQuerys.getTableData(1)));

        for (Table table:dbQuerys.getTableData(1)){
            System.out.println(table.getColumnName()+table.getDescription()+table.getRowName()+table.getOrder());
        }
        ServerConnection connection = new ServerConnection();
        ResultSet rs = connection.query ("SELECT * FROM test");


        System.out.println(rs.getMetaData().getColumnName(1) + " " + rs.getMetaData().getColumnName(2));
        rs.next();
        System.out.println(rs.getInt("id") + " " + rs.getString("firstname"));
        rs.close();*/
    }
}