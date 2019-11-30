package server.Controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import server.ConverterJSON;
import server.TestRecive.Person;
import server.dbQuerys;
import server.model.Board;

import java.sql.SQLException;

//Routing controller
@Controller("/board")
public class BoardController {

    @Get()
    public String boardList() {
        try {
            return ConverterJSON.boardListToJSONWithoutEmpty(dbQuerys.getBoards());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "404";
    }

    @Get("/{number}")
    public String board(@PathVariable Integer number) {
        try {
            return ConverterJSON.boardListToJSON(dbQuerys.getTableData(number));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "404";
    }

    //Aktualizacja nazwy tabeli
    // z id board
    // curl -d {\"board_id\":\"1\",\"board_name\":\"value\"} -H "Content-Type: application/json" -X POST "http://localhost:8080/board/1/updateName"
    //bez id boarda
    // curl -d {\"board_name\":\"value\"} -H "Content-Type: application/json" -X POST "http://localhost:8080/board/1/updateName"
    @Post("/{number}/updateName")
    public HttpResponse<Board> updateBoard(@PathVariable Integer number,@Body Board board) {
        try {
            dbQuerys.updateBoard(number,board.getBoard_name());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return HttpResponse.ok();
    }

    //Dodanie nowej tabeli
    //curl -d {\"board_name\":\"value\"} -H "Content-Type: application/json" -X POST "http://localhost:8080/board/insert"
    @Post("/insert")
    public HttpResponse<Board> insertBoard(@Body Board board) {
        try {
            dbQuerys.insertBoard(board.getBoard_name());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return HttpResponse.ok();
    }
}