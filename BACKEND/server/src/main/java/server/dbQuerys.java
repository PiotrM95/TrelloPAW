package server;

import server.model.Board;
import server.model.Row;
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
    public static List<Board> getTableData(int id) throws SQLException {
        Initialize();
        StringBuilder string = new StringBuilder();
        string.append(" SELECT T.id AS idB, T.Name AS nameB,C.Name as CName, C.Order as `Order`, R.Name as RName, 0 as row_id");
        string.append(" FROM `Table` T");
        string.append(" INNER JOIN `Column` C ON C.IdTable=T.id");
        string.append(" INNER JOIN `Row` R ON R.IdColumn=C.Id");
        string.append(" WHERE T.id ="+id);
        ResultSet rs = connection.query(string.toString());

        List<Board> list = new ArrayList<>();
        while(rs.next()){
            boolean isInList = false;
            Board board=new Board();
            //Sprawdzenie czy istnieje juz wpis o boardzie dunno po co to robie
            for (Board element: list) {
                if (element.getBoard_id() == rs.getInt("idB")) {
                    isInList = true;
                    board = element;
                }
            }
            if(!isInList) {
                //board = new Board();
                board.setBoard_id(rs.getInt("idB"));
                board.setBoard_name(rs.getString("nameB"));
                board.setLists(new ArrayList<>());
                list.add(board);
                System.out.println("fuck");
            }

            //Sprawdzenie czy istnieje juz wpis o liscie
            server.model.List list1=new server.model.List();
            isInList = false;
            for (server.model.List element: board.getLists()) {
                if (element.getList_name().equals(rs.getString("CName"))) {
                    isInList = true;
                    list1 = element;
                }
            }
            if(!isInList){
                server.model.List newList = new server.model.List();
                newList.setList_id(rs.getInt("Order"));
                newList.setList_name(rs.getString("CName"));
                newList.setRows(new ArrayList<>());
                board.getLists().add(newList);
                list1=newList;
            }

            //Dodanie Row
            Row row = new Row();
            row.setRow_id(rs.getInt("row_id"));
            row.setRow_name(rs.getString("RName"));
            list1.getRows().add(row);
        }

        rs.close();
        return list;
    }
}
