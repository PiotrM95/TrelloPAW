package server;

import com.mysql.cj.jdbc.CallableStatement;
import server.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dbQuerys {
    static ServerConnection connection;
    static String url;
    static String password;
    static String username;

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

    public static void insertRow(int boardId,int listOrder,int rowOrder, String rowName) throws SQLException {
        Initialize();

        CallableStatement statement = connection.procedureCall("{ call InsertRow(?,?,?,?) }");
        statement.setInt("boardId", boardId);
        statement.setInt("listOrder", listOrder);
        statement.setInt("rowOrder", rowOrder);
        statement.setString("rowName", rowName);
        ResultSet rs = connection.procedureExecute(statement);

        rs.close();
    }

    public static Details getDetails(int boardId, int listOrder, int rowOrder) throws SQLException {
        Initialize();
//        CallableStatement statement = connection.procedureCall("{ call getBoard(?) }");
//        statement.setInt("boardId", id);
//        ResultSet rs = connection.procedureExecute(statement);

        CallableStatement statement = connection.procedureCall("{ call GetRowDetails(?,?,?) }");
        statement.setInt("boardId", boardId);
        statement.setInt("listOrder", listOrder);
        statement.setInt("rowOrder", rowOrder);
        ResultSet rs = connection.procedureExecute(statement);


        Details details= new Details();
        while(rs.next()){
            if(details.getRow().getRow_name()==null || details.getRow().getRow_description()==null)
            {
                details.getRow().setRow_name(rs.getString("row_name"));
                String description = rs.getString("row_description");

                if(rs.wasNull()){
                    description=null;
                }
                details.getRow().setRow_description(description);
            }
            Comment comment = new Comment();
            comment.setComment_id(rs.getInt("comment_id"));
            comment.setComment_name(rs.getString("comment_name"));
            details.getComments().add(comment);
        }

        rs.close();
        return details;
    }

    public static void insertFile() throws SQLException, FileNotFoundException {
        Attachment attachment = new Attachment();
        File file = attachment.getFile();
        Initialize();
        Connection conn = DriverManager.getConnection(url, username, password);
        InputStream inputStream = new FileInputStream(file);
        String sql = "INSERT INTO attachment values (?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setBlob(1, inputStream);
        statement.executeUpdate();

    }

    public static boolean checkExistUser(String login) throws SQLException {
        Initialize();
        Connection conn = DriverManager.getConnection(url, username, password);
        Statement stmt = conn.createStatement();
        String sqlLogin = "Select Login from User";
        ResultSet rs = stmt.executeQuery(sqlLogin);

        if (rs.next() == false) {
            return false;
        }

        rs.close();
        return true;
    }

    public static void setNewUser(String login, String pass) throws SQLException {
        Initialize();
        Connection conn = DriverManager.getConnection(url, username, password);
        Statement stmt = conn.createStatement();
        String newUser = "Insert into User values(" + login + ", " + pass + ", false)";
        stmt.executeUpdate(newUser);
    }

    public static void deleteAttachment(String attachment) throws SQLException {
        Initialize();
        Connection conn = DriverManager.getConnection(url, username, password);
        Statement stmt = conn.createStatement();
        String sql = "delete from attachment where file_data=" + attachment;
        stmt.executeUpdate(sql);
    }
}
