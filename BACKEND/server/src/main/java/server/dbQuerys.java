package server;

import com.mysql.cj.jdbc.CallableStatement;
import server.model.Board;
import server.model.Row;
import server.model.Table;

import java.sql.*;
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
        /*StringBuilder string = new StringBuilder();
        string.append(" SELECT T.id AS idB, T.Name AS nameB,C.Name as CName, C.Order as `Order`, R.Name as RName, 0 as row_id");
        string.append(" FROM `Table` T");
        string.append(" INNER JOIN `Column` C ON C.IdTable=T.id");
        string.append(" INNER JOIN `Row` R ON R.IdColumn=C.Id");
        string.append(" WHERE T.id ="+id);
        ResultSet rs = connection.query(string.toString());*/
        CallableStatement statement = connection.procedureCall("{ call getBoard(?) }");
        statement.setInt("boardId", id);
        ResultSet rs = connection.procedureExecute(statement);
        //statment.setInt(1, candidateId);



        List<Board> list = new ArrayList<>();
        while(rs.next()){
            boolean isInList = false;
            Board board=new Board();
            //Sprawdzenie czy istnieje juz wpis o boardzie dunno po co to robie
            for (Board element: list) {
                if (element.getBoard_id() == rs.getInt("board_id")) {
                    isInList = true;
                    board = element;
                }
            }
            if(!isInList) {
                //board = new Board();
                board.setBoard_id(rs.getInt("board_id"));
                board.setBoard_name(rs.getString("board_name"));
                board.setLists(new ArrayList<>());
                list.add(board);
            }

            //Sprawdzenie czy istnieje juz wpis o liscie
            server.model.List list1=new server.model.List();
            isInList = false;
            for (server.model.List element: board.getLists()) {
                if (element.getList_name().equals(rs.getString("list_name"))) {
                    isInList = true;
                    list1 = element;
                }
            }
            if(!isInList){
                server.model.List newList = new server.model.List();
                newList.setList_id(rs.getInt("list_id"));
                newList.setList_name(rs.getString("list_name"));
                newList.setRows(new ArrayList<>());
                board.getLists().add(newList);
                list1=newList;
            }

            //Dodanie Row
            Row row = new Row();
            row.setRow_id(rs.getInt("row_id"));
            row.setRow_name(rs.getString("row_name"));
            list1.getRows().add(row);
        }

        rs.close();
        return list;
    }

    public static boolean checkExistUser(String login) throws SQLException {
        Initialize();
        Connection conn = DriverManager.getConnection("dodac url tu","username","password");
        Statement stmt = conn.createStatement();
        String sqlLogin = "Select Login from User";
        ResultSet rs = stmt.executeQuery(sqlLogin);

        if (rs.next() == false) {
            return false;
        }

        rs.close();
        return true;
    }

    public static void setNewUser(String login, String password) throws SQLException {
        Initialize();
        Connection conn = DriverManager.getConnection("dodac url tu","username","password");
        Statement stmt = conn.createStatement();
        String newUser = "Insert into User values(" + login + ", " + password + ", false)";
        stmt.executeUpdate(newUser);
    }
}
