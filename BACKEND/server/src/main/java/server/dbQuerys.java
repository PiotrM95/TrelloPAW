package server;

import com.mysql.cj.jdbc.CallableStatement;
import server.model.Board;
import server.model.Row;

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

    public static List<Board> getBoards() throws SQLException {
        Initialize();
//        CallableStatement statement = connection.procedureCall("{ call getBoard(?) }");
//        statement.setInt("boardId", id);
//        ResultSet rs = connection.procedureExecute(statement);

        CallableStatement statement = connection.procedureCall("{ call getBoards }");
        ResultSet rs = connection.procedureExecute(statement);


        List<Board> list = new ArrayList<>();
        while(rs.next()){
            Board board=new Board();
            board.setBoard_id(rs.getInt("board_id"));
            board.setBoard_name(rs.getString("board_name"));
            board.setLists(new ArrayList<>());
            list.add(board);
        }

        rs.close();
        return list;
    }

    public static void updateBoard(int id, String name) throws SQLException {
        Initialize();

        CallableStatement statement = connection.procedureCall("{ call UpdateBoard(?,?) }");
        statement.setInt("boardId", id);
        statement.setString("boardName", name);
        ResultSet rs = connection.procedureExecute(statement);

        rs.close();
    }

    public static void insertBoard(String name) throws SQLException {
        Initialize();

        CallableStatement statement = connection.procedureCall("{ call InsertBoard(?) }");
        statement.setString("boardName", name);
        ResultSet rs = connection.procedureExecute(statement);

        rs.close();
    }
}
