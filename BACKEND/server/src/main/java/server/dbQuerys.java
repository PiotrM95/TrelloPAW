package server;

import server.model.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class dbQuerys {
    static ServerConnection connection;

    ///Utworzenia połączenia z serwerem
    static void Initialize(){
        try {
            connection = new ServerConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ///Pobranie danych z servera i wrzucenie ich do listy obiektow Table
    ///id - id(numer) tabeli
    public static List<Table> getTableData(int id) throws SQLException {
        Initialize();
        StringBuilder string = new StringBuilder();
        string.append(" SELECT C.Name as CName, C.Order as `Order`, R.Name as RName, R.Description as Description");
        string.append(" FROM `Table` T");
        string.append(" INNER JOIN `Column` C ON C.IdTable=T.id");
        string.append(" INNER JOIN `Row` R ON R.IdColumn=C.Id");
        string.append(" WHERE T.id ="+id);
        ResultSet rs = connection.query(string.toString());

        List<Table> list = new ArrayList<>();
        while(rs.next()){
            Table table = new Table();
            table.setColumnName(rs.getString("CName"));
            table.setOrder(rs.getInt("Order"));
            table.setRowName(rs.getString("RName"));
            table.setDescription(rs.getString("Description"));
            list.add(table);
        }
        rs.close();
        return list;
    }
}
