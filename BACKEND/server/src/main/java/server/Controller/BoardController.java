package server.Controller;

import io.micronaut.http.HttpHeaders;
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

    //zwraca liste tablic
    @Get()
    public HttpResponse<String> boardList() {
        try {
            return HttpResponse.ok(ConverterJSON.boardListToJSONWithoutEmpty(dbQuerys.getBoards()));//.header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return HttpResponse.serverError();
    }

    //zwraca tablice
    @Get("/{number}")
    public HttpResponse<String> board(@PathVariable Integer number) {
        try {
            return HttpResponse.ok(ConverterJSON.boardListToJSON(dbQuerys.getTableData(number)));//.header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return HttpResponse.serverError();
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
    public HttpResponse insertBoard(@Body Board board) {
        try {
            dbQuerys.insertBoard(board.getBoard_name());
            //return HttpResponse.ok().header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*").header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, GET")
              //      .header(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Accept");
            return HttpResponse.ok();
        } catch (SQLException e) {
            return HttpResponse.serverError();
        }

    }

    //Dodadaje nowa karte
    //curl -d {\"lists\":[{\"list_id\":\"1\",\"rows\":[{\"row_id\":\"4\",\"row_name\":\"nazwaRow\"}]}]} -H "Content-Type: application/json" -X POST "http://localhost:8080/board/1/insertRow"
    @Post("/{number}/insertRow")
    public HttpResponse insertRow(@PathVariable Integer number,@Body Board board) {
        try {
            dbQuerys.insertRow(number,board.getLists().get(0).getList_id(),board.getLists().get(0).getRows().get(0).getRow_id(),board.getLists().get(0).getRows().get(0).getRow_name());
            //return HttpResponse.ok().header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*").header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, GET")
              //      .header(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Accept");
            return HttpResponse.ok();
        } catch (SQLException e) {
            return HttpResponse.serverError();
        }
    }


    //Zwraca szczegóły
    @Get("/{boardId}/{listOrder}+{rowOrder}")
    public HttpResponse<String> details(@PathVariable Integer boardId, @PathVariable Integer listOrder, @PathVariable Integer rowOrder) {
        try {
            return HttpResponse.ok(ConverterJSON.detailsToJSON(dbQuerys.getDetails(boardId,listOrder,rowOrder)));//.header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        } catch (SQLException e) {
            return HttpResponse.serverError();
        }

    }

    //Zmiana pozycji karty
    //curl -d {\"lists\":[{\"list_id\":\"1\",\"rows\":[{\"row_id\":\"1\",\"row_name\":\"nazwaRow\"}]}]} -H "Content-Type: application/json" -X POST "http://localhost:8080/board/1/moveRow+3"
    @Post("/{number}/moveRow+{newRowOrder}")
    public HttpResponse moveRow(@PathVariable Integer number,@PathVariable Integer newRowOrder,@Body Board board) {
        try {
            dbQuerys.moveRow(number,board.getLists().get(0).getList_id(),board.getLists().get(0).getRows().get(0).getRow_id(),newRowOrder);
            return HttpResponse.ok();
        } catch (SQLException e) {
            return HttpResponse.serverError();
        }
    }

    //Zmiana pozycji listy
    //curl -d {\"lists\":[{\"list_id\":\"1\",\"rows\":[{\"row_id\":\"1\",\"row_name\":\"nazwaRow\"}]}]} -H "Content-Type: application/json" -X POST "http://localhost:8080/board/1/moveRow+3"
    @Post("/{number}/moveList+{newListOrder}")
    public HttpResponse moveList(@PathVariable Integer number,@PathVariable Integer newListOrder,@Body Board board) {
        try {
            dbQuerys.moveList(number,board.getLists().get(0).getList_id(),newListOrder);
            return HttpResponse.ok();
        } catch (SQLException e) {
            return HttpResponse.serverError();
        }
    }
}