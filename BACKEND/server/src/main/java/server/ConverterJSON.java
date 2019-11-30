package server;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.model.Board;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_EMPTY_JSON_ARRAYS;

public class ConverterJSON {

    ///Funkcja zamienia List<Table> na String JSON
    public static String boardListToJSON(List<Board> list){

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();


        try {
            mapper.writeValue(out, list);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final byte[] data = out.toByteArray();
        return(new String(data));
    }

    public static String boardListToJSONWithoutEmpty(List<Board> list){

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();

        mapper.configure(WRITE_EMPTY_JSON_ARRAYS,false);
        try {
            mapper.writeValue(out, list);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final byte[] data = out.toByteArray();
        return(new String(data));
    }


}
