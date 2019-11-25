package server.Controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import server.ConverterJSON;
import server.dbQuerys;

import java.sql.SQLException;

//Routing controller
@Controller("/board")
public class BoardController {

    @Get("/{number}")
    public String board(@PathVariable Integer number) {
        try {
            return ConverterJSON.tableListToJSON(dbQuerys.getTableData(number));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "404";
    }
    @Get(produces = MediaType.TEXT_PLAIN)
    public String boardList(@PathVariable Integer number) {
        try {
            return ConverterJSON.tableListToJSON(dbQuerys.getTableData(number));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "404";
    }
}